
	import { BASE_URL, DEFAULT_FOOD_IMAGE } from '@/common/config.uts'
	
	type CartItem = {
		id: number,
		product_id: number,
		name: string,
		price: number,
		quantity: number,
		images: Array<string>,
		specs?: any,
		notes?: string
	}
	
	const __sfc__ = defineComponent({
		data() {
			return {
				cartItems: [] as Array<CartItem>,
				orderInfo: {
					name: '',
					phone: '',
					address: '',
					remark: '',
					tableNumber: ''
				},
				orderType: 'delivery',
				typeOptions: [
					{ value: 'delivery', label: '外卖' },
					{ value: 'pickup', label: '自取' }
				],
				shopInfo: {
					delivery_fee: 0,
					min_order_amount: 0
				}
			}
		},
		computed: {
			subtotal(): number {
				return this.cartItems.reduce((sum: number, item: CartItem) => sum + (item.price || 0) * item.quantity, 0)
			},
			deliveryFee(): number {
				return this.orderType === 'delivery' ? (this.shopInfo.delivery_fee || 0) : 0
			},
			totalAmount(): number {
				return this.subtotal + this.deliveryFee
			}
		},
		onLoad() {
			this.fetchCart()
			this.fetchDefaultAddress()
			this.fetchShopInfo()
			this.fillContactFromStorage()
		},
		methods: {
			fetchShopInfo() {
				uni.request({
					url: `${BASE_URL}/shop/info`,
					method: 'GET',
					success: (res: any) => {
						if (res.data.success && res.data.data) {
							this.shopInfo = res.data.data
						}
					}
				})
			},
			
			fillContactFromStorage() {
				const user = uni.getStorageSync('userInfo')
				if (user) {
					if (!this.orderInfo.name) this.orderInfo.name = user.nickname || user.username || ''
					if (!this.orderInfo.phone && user.phone) this.orderInfo.phone = user.phone
				}
			},

			changeOrderType(type: string) {
				this.orderType = type
			},

			getOrderTypeLabel(type: string) {
				const found = this.typeOptions.find((item: any) => item.value === type)
				return found ? found.label : '外卖'
			},

			selectAddress() {
				uni.navigateTo({ url: '/pages/address/list?select=true' })
			},
			
			setAddress(address: any) {
				this.orderInfo.name = address.contact_name || address.name
				this.orderInfo.phone = address.contact_phone || address.phone
				this.orderInfo.address = address.address || address.detail || ''
			},
			
			fetchDefaultAddress() {
				const token = uni.getStorageSync('token')
				if (!token) return
				
				uni.request({
					url: `${BASE_URL}/addresses/default`,
					method: 'GET',
					header: { 'Authorization': `Bearer ${token}` },
					success: (res: any) => {
						if (res.data.success && res.data.data.address) {
							this.setAddress(res.data.data.address)
						}
					}
				})
			},

			getProductImage(item: CartItem): string {
				if (item.images && item.images.length > 0) {
					return item.images[0]
				}
				return DEFAULT_FOOD_IMAGE
			},
			
			fetchCart() {
				const token = uni.getStorageSync('token')
				if (!token) {
					uni.navigateTo({ url: '/pages/login/login' })
					return
				}
				
				uni.request({
					url: `${BASE_URL}/cart`,
					method: 'GET',
					header: { 'Authorization': `Bearer ${token}` },
					success: (res: any) => {
						if (res.data.success && res.data.data.cart) {
							const items = res.data.data.cart.items || []
							this.cartItems = items.map((item: any) => ({
								id: item.cart_item_id,
								product_id: item.product_id,
								name: item.name,
								price: item.price,
								quantity: item.quantity,
								images: item.images || [],
								specs: item.specs || item.options || undefined,
								notes: item.notes || ''
							}))
						}
					}
				})
			},
			
			async submitOrder() {
				if (this.orderType === 'delivery') {
					if (!this.orderInfo.name || !this.orderInfo.phone || !this.orderInfo.address) {
						uni.showToast({ title: '请先选择收货地址', icon: 'none' })
						return
					}
					// 检查起送金额
					const minAmount = this.shopInfo.min_order_amount || 0
					if (minAmount > 0 && this.subtotal < minAmount) {
						uni.showToast({ title: `商品金额不满足起送金额 ¥${minAmount}`, icon: 'none' })
						return
					}
				}
				if (this.orderType === 'pickup') {
					if (!this.orderInfo.name || !this.orderInfo.phone) {
						uni.showToast({ title: '请先选择地址或登录补全联系人', icon: 'none' })
						return
					}
				}
				if (this.cartItems.length === 0) {
					uni.showToast({ title: '购物车为空', icon: 'none' })
					return
				}
				
				const token = uni.getStorageSync('token')
				uni.showLoading({ title: '提交中...' })
				
				try {
					const payload: any = {
						order_type: this.orderType,
						notes: this.orderInfo.remark,
						items: this.cartItems.map((item: CartItem) => {
							const mapped: any = {
								product_id: item.product_id,
								quantity: item.quantity
							}
							if (item.specs) mapped.specs = item.specs
							if (item.notes) mapped.notes = item.notes
							return mapped
						}),
						cart_item_ids: this.cartItems.map((item: CartItem) => item.id)
					}

					if (this.orderType === 'delivery') {
						payload.delivery_address = this.orderInfo.address
						payload.contact_name = this.orderInfo.name
						payload.contact_phone = this.orderInfo.phone
					} else if (this.orderType === 'pickup') {
						payload.contact_name = this.orderInfo.name
						payload.contact_phone = this.orderInfo.phone
					}

					const createRes: any = await new Promise((resolve, reject) => {
						uni.request({
							url: `${BASE_URL}/orders`,
							method: 'POST',
							header: {
								'Authorization': `Bearer ${token}`,
								'Content-Type': 'application/json'
							},
							data: payload,
							success: resolve,
							fail: reject
						})
					})

					if (!createRes.data?.success) {
						uni.showToast({ title: createRes.data?.message || '下单失败', icon: 'none' })
						return
					}

					const orderId = createRes.data?.data?.order?.id || createRes.data?.data?.id || createRes.data?.data?.order_id
					if (!orderId) {
						uni.showToast({ title: '下单成功，但未获取订单ID', icon: 'none' })
						uni.switchTab({ url: '/pages/order/list' })
						return
					}

					const payRes: any = await new Promise((resolve, reject) => {
						uni.request({
							url: `${BASE_URL}/orders/${orderId}/pay`,
							method: 'POST',
							header: { 'Authorization': `Bearer ${token}` },
							success: resolve,
							fail: reject
						})
					})

					if (payRes.data?.success) {
						uni.showToast({ title: '支付成功', icon: 'success' })
						setTimeout(() => {
							// 详情 -> 用户返回时直接回到首页
							uni.redirectTo({ url: `/pages/order/detail?id=${orderId}` })
						}, 800)
					} else {
						uni.showToast({ title: payRes.data?.message || '支付失败', icon: 'none' })
					}
				} catch (e) {
					uni.showToast({ title: '网络错误', icon: 'none' })
				} finally {
					uni.hideLoading()
				}
			}
		}
	})

export default __sfc__
function GenPagesOrderCreateRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "section" }), [
      _cE("text", _uM({ class: "section-title" }), "用餐方式"),
      _cE("view", _uM({ class: "type-options" }), [
        _cE(Fragment, null, RenderHelpers.renderList(_ctx.typeOptions, (item, __key, __index, _cached): any => {
          return _cE("view", _uM({
            class: _nC(["type-chip", _uM({ active: _ctx.orderType === item.value })]),
            key: item.value,
            onClick: () => {_ctx.changeOrderType(item.value)}
          }), [
            _cE("text", _uM({ class: "type-text" }), _tD(item.label), 1 /* TEXT */)
          ], 10 /* CLASS, PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */)
      ])
    ]),
    _cE("view", _uM({
      class: "section address-section",
      onClick: _ctx.selectAddress
    }), [
      _cE("view", _uM({ class: "section-header-row" }), [
        _cE("text", _uM({ class: "section-title" }), "配送信息"),
        _cE("text", _uM({ class: "arrow" }), "➜")
      ]),
      _ctx.orderType === 'delivery'
        ? _cE(Fragment, _uM({ key: 0 }), [
            isTrue(_ctx.orderInfo.address)
              ? _cE("view", _uM({
                  key: 0,
                  class: "address-display"
                }), [
                  _cE("view", _uM({ class: "contact-row" }), [
                    _cE("text", _uM({ class: "contact-name" }), _tD(_ctx.orderInfo.name), 1 /* TEXT */),
                    _cE("text", _uM({ class: "contact-phone" }), _tD(_ctx.orderInfo.phone), 1 /* TEXT */)
                  ]),
                  _cE("text", _uM({ class: "address-text" }), _tD(_ctx.orderInfo.address), 1 /* TEXT */)
                ])
              : _cE("view", _uM({
                  key: 1,
                  class: "address-placeholder"
                }), [
                  _cE("view", _uM({ class: "placeholder-icon-box" }), [
                    _cE("image", _uM({
                      class: "placeholder-icon",
                      src: "/static/icons/icon-address.svg",
                      mode: "aspectFit"
                    }))
                  ]),
                  _cE("text", _uM({ class: "placeholder-text" }), "请选择收货地址")
                ])
          ], 64 /* STABLE_FRAGMENT */)
        : _cE(Fragment, _uM({ key: 1 }), [
            isTrue(_ctx.orderInfo.name || _ctx.orderInfo.phone)
              ? _cE("view", _uM({
                  key: 0,
                  class: "address-display"
                }), [
                  _cE("view", _uM({ class: "contact-row" }), [
                    _cE("text", _uM({ class: "contact-name" }), _tD(_ctx.orderInfo.name), 1 /* TEXT */),
                    _cE("text", _uM({ class: "contact-phone" }), _tD(_ctx.orderInfo.phone), 1 /* TEXT */)
                  ])
                ])
              : _cE("view", _uM({
                  key: 1,
                  class: "address-placeholder"
                }), [
                  _cE("view", _uM({ class: "placeholder-icon-box" }), [
                    _cE("image", _uM({
                      class: "placeholder-icon",
                      src: "/static/icons/icon-address.svg",
                      mode: "aspectFit"
                    }))
                  ]),
                  _cE("text", _uM({ class: "placeholder-text" }), "自取：请选择地址以填充联系人信息")
                ])
          ], 64 /* STABLE_FRAGMENT */)
    ], 8 /* PROPS */, ["onClick"]),
    _cE("view", _uM({ class: "section" }), [
      _cE("text", _uM({ class: "section-title" }), "商品清单"),
      _cE("view", _uM({ class: "order-items" }), [
        _cE(Fragment, null, RenderHelpers.renderList(_ctx.cartItems, (item, __key, __index, _cached): any => {
          return _cE("view", _uM({
            class: "order-item",
            key: item.id
          }), [
            _cE("image", _uM({
              class: "item-image",
              src: _ctx.getProductImage(item),
              mode: "aspectFill"
            }), null, 8 /* PROPS */, ["src"]),
            _cE("view", _uM({ class: "item-info" }), [
              _cE("text", _uM({ class: "item-name" }), _tD(item.name || '商品'), 1 /* TEXT */),
              _cE("view", _uM({ class: "item-bottom" }), [
                _cE("text", _uM({ class: "item-price" }), "￥" + _tD(item.price || 0), 1 /* TEXT */),
                _cE("text", _uM({ class: "item-qty" }), "x" + _tD(item.quantity), 1 /* TEXT */)
              ])
            ])
          ])
        }), 128 /* KEYED_FRAGMENT */)
      ])
    ]),
    _cE("view", _uM({ class: "section" }), [
      _cE("text", _uM({ class: "section-title" }), "订单备注"),
      _cE("textarea", _uM({
        class: "remark-input",
        modelValue: _ctx.orderInfo.remark,
        onInput: ($event: UniInputEvent) => {(_ctx.orderInfo.remark) = $event.detail.value},
        placeholder: "如需备注请输入（如：少辣、不要香菜等）"
      }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
    ]),
    _cE("view", _uM({ class: "section amount-section" }), [
      _cE("view", _uM({ class: "amount-row" }), [
        _cE("text", _uM({ class: "amount-label" }), "商品金额"),
        _cE("text", _uM({ class: "amount-value" }), "￥" + _tD(_ctx.subtotal.toFixed(2)), 1 /* TEXT */)
      ]),
      _ctx.orderType === 'delivery'
        ? _cE("view", _uM({
            key: 0,
            class: "amount-row"
          }), [
            _cE("text", _uM({ class: "amount-label" }), "配送费"),
            _cE("text", _uM({ class: "amount-value" }), "￥" + _tD(_ctx.deliveryFee.toFixed(2)), 1 /* TEXT */)
          ])
        : _cC("v-if", true),
      isTrue(_ctx.orderType === 'delivery' && _ctx.shopInfo.min_order_amount > 0)
        ? _cE("view", _uM({
            key: 1,
            class: "amount-row hint-row"
          }), [
            _cE("text", _uM({ class: "hint-text" }), "起送金额 ¥" + _tD(_ctx.shopInfo.min_order_amount), 1 /* TEXT */),
            _cE("text", _uM({
              class: _nC(["hint-status", _ctx.subtotal >= _ctx.shopInfo.min_order_amount ? 'success' : 'warning'])
            }), _tD(_ctx.subtotal >= _ctx.shopInfo.min_order_amount ? '已满足' : '未满足'), 3 /* TEXT, CLASS */)
          ])
        : _cC("v-if", true),
      _cE("view", _uM({ class: "amount-row total-row" }), [
        _cE("text", _uM({ class: "amount-label" }), "合计"),
        _cE("text", _uM({ class: "total-value" }), "￥" + _tD(_ctx.totalAmount.toFixed(2)), 1 /* TEXT */)
      ])
    ]),
    _cE("view", _uM({ class: "submit-bar" }), [
      _cE("view", _uM({ class: "total-box" }), [
        _cE("text", _uM({ class: "total-label" }), "待支付："),
        _cE("text", _uM({ class: "total-amount" }), "￥" + _tD(_ctx.totalAmount.toFixed(2)), 1 /* TEXT */)
      ]),
      _cE("view", _uM({
        class: "submit-btn",
        onClick: _ctx.submitOrder
      }), [
        _cE("text", _uM({ class: "submit-text" }), "提交订单 ✔")
      ], 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesOrderCreateStyles = [_uM([["container", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#FFF5F8"], ["paddingBottom", "180rpx"]]))], ["section", _pS(_uM([["backgroundColor", "rgba(255,255,255,0.9)"], ["marginTop", "24rpx"], ["marginRight", "24rpx"], ["marginBottom", "24rpx"], ["marginLeft", "24rpx"], ["paddingTop", "36rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "36rpx"], ["paddingLeft", "36rpx"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"]]))], ["section-title", _pS(_uM([["fontSize", "32rpx"], ["color", "#5D4E6D"], ["borderLeftWidth", "8rpx"], ["borderLeftStyle", "solid"], ["borderLeftColor", "#FF69B4"], ["paddingLeft", "20rpx"]]))], ["section-header-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginBottom", "24rpx"]]))], ["arrow", _pS(_uM([["fontSize", "40rpx"], ["color", "#B8A9C9"]]))], ["type-options", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["marginTop", "24rpx"], ["gap", "16rpx"]]))], ["type-chip", _uM([["", _uM([["paddingTop", "20rpx"], ["paddingRight", "28rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "28rpx"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["backgroundColor", "rgba(255,255,255,0.8)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["boxShadow", "0 6rpx 16rpx rgba(255, 105, 180, 0.1)"], ["transitionProperty", "all"], ["transitionDuration", "0.2s"]])], [".active", _uM([["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["color", "#ffffff"], ["borderTopColor", "rgba(0,0,0,0)"], ["borderRightColor", "rgba(0,0,0,0)"], ["borderBottomColor", "rgba(0,0,0,0)"], ["borderLeftColor", "rgba(0,0,0,0)"], ["boxShadow", "0 8rpx 20rpx rgba(255, 105, 180, 0.3)"]])]])], ["type-text", _pS(_uM([["fontSize", "28rpx"], ["fontWeight", "700"]]))], ["table-input", _pS(_uM([["marginTop", "20rpx"], ["width", "70%"], ["minWidth", "400rpx"], ["paddingTop", "20rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.8)"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["boxShadow", "0 6rpx 16rpx rgba(255, 105, 180, 0.08)"], ["fontSize", "30rpx"], ["color", "#5D4E6D"], ["boxSizing", "border-box"]]))], ["address-display", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["paddingTop", "10rpx"], ["paddingRight", 0], ["paddingBottom", "10rpx"], ["paddingLeft", 0]]))], ["contact-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["marginBottom", "12rpx"]]))], ["contact-name", _pS(_uM([["fontSize", "34rpx"], ["color", "#5D4E6D"], ["marginRight", "20rpx"]]))], ["contact-phone", _pS(_uM([["fontSize", "28rpx"], ["color", "#888888"]]))], ["address-text", _pS(_uM([["fontSize", "30rpx"], ["color", "#666666"], ["lineHeight", 1.5]]))], ["address-placeholder", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0]]))], ["placeholder-icon-box", _pS(_uM([["width", "60rpx"], ["height", "60rpx"], ["backgroundColor", "#FFF0F5"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["marginRight", "20rpx"]]))], ["placeholder-icon", _pS(_uM([["width", "32rpx"], ["height", "32rpx"]]))], ["placeholder-text", _pS(_uM([["fontSize", "30rpx"], ["color", "#B8A9C9"]]))], ["order-items", _pS(_uM([["paddingTop", 0], ["paddingRight", 0], ["paddingBottom", 0], ["paddingLeft", 0]]))], ["order-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["borderBottomWidth:last-child", "medium"], ["borderBottomStyle:last-child", "none"], ["borderBottomColor:last-child", "#000000"]]))], ["item-image", _pS(_uM([["width", "140rpx"], ["height", "140rpx"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["borderTopWidth", "4rpx"], ["borderRightWidth", "4rpx"], ["borderBottomWidth", "4rpx"], ["borderLeftWidth", "4rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFF0F5"], ["borderRightColor", "#FFF0F5"], ["borderBottomColor", "#FFF0F5"], ["borderLeftColor", "#FFF0F5"], ["boxShadow", "0 4rpx 12rpx rgba(0,0,0,0.05)"]]))], ["item-info", _pS(_uM([["flex", 1], ["marginLeft", "24rpx"], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "space-between"], ["height", "140rpx"]]))], ["item-name", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"], ["fontWeight", "700"]]))], ["item-bottom", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"]]))], ["item-price", _pS(_uM([["fontSize", "32rpx"], ["color", "#FF69B4"]]))], ["item-qty", _pS(_uM([["fontSize", "28rpx"], ["color", "#B8A9C9"]]))], ["remark-input", _pS(_uM([["width", "100%"], ["height", "180rpx"], ["backgroundColor", "#FFF5F8"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["paddingTop", "24rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "24rpx"], ["fontSize", "30rpx"], ["boxSizing", "border-box"], ["color", "#5D4E6D"], ["borderTopWidth", 2], ["borderRightWidth", 2], ["borderBottomWidth", 2], ["borderLeftWidth", 2], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFE4EC"], ["borderRightColor", "#FFE4EC"], ["borderBottomColor", "#FFE4EC"], ["borderLeftColor", "#FFE4EC"]]))], ["amount-section", _pS(_uM([["paddingTop", "30rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "36rpx"]]))], ["amount-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["paddingTop", "16rpx"], ["paddingRight", 0], ["paddingBottom", "16rpx"], ["paddingLeft", 0]]))], ["amount-label", _pS(_uM([["fontSize", "30rpx"], ["color", "#B8A9C9"]]))], ["amount-value", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["hint-row", _pS(_uM([["backgroundColor", "#FFF8F0"], ["marginTop", "10rpx"], ["marginRight", "-20rpx"], ["marginBottom", "10rpx"], ["marginLeft", "-20rpx"], ["paddingTop", "12rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "12rpx"], ["paddingLeft", "20rpx"], ["borderTopLeftRadius", "12rpx"], ["borderTopRightRadius", "12rpx"], ["borderBottomRightRadius", "12rpx"], ["borderBottomLeftRadius", "12rpx"]]))], ["hint-text", _pS(_uM([["fontSize", "26rpx"], ["color", "#B8A9C9"]]))], ["hint-status", _uM([["", _uM([["fontSize", "26rpx"]])], [".success", _uM([["color", "#52c41a"]])], [".warning", _uM([["color", "#faad14"]])]])], ["total-row", _pS(_uM([["marginTop", "20rpx"], ["paddingTop", "24rpx"], ["borderTopWidth", 2], ["borderTopStyle", "dashed"], ["borderTopColor", "#FFE4EC"]]))], ["total-value", _pS(_uM([["fontSize", "40rpx"], ["color", "#FF69B4"]]))], ["submit-bar", _pS(_uM([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["justifyContent", "space-between"], ["paddingTop", "24rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "36rpx"], ["marginTop", "24rpx"], ["marginRight", "24rpx"], ["marginBottom", "24rpx"], ["marginLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.95)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 -4rpx 30rpx rgba(255, 105, 180, 0.2)"], ["zIndex", 100], ["backdropFilter", "blur(10px)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.5)"], ["borderRightColor", "rgba(255,255,255,0.5)"], ["borderBottomColor", "rgba(255,255,255,0.5)"], ["borderLeftColor", "rgba(255,255,255,0.5)"]]))], ["total-box", _pS(_uM([["display", "flex"], ["flexDirection", "row"]]))], ["total-label", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["total-amount", _pS(_uM([["fontSize", "44rpx"], ["color", "#FF69B4"], ["marginLeft", "10rpx"]]))], ["submit-btn", _pS(_uM([["paddingTop", "28rpx"], ["paddingRight", "64rpx"], ["paddingBottom", "28rpx"], ["paddingLeft", "64rpx"], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.4)"], ["transitionProperty", "transform"], ["transitionDuration", "0.2s"], ["transform:active", "scale(0.95)"]]))], ["submit-text", _pS(_uM([["fontSize", "32rpx"], ["color", "#ffffff"], ["letterSpacing", 1]]))], ["@TRANSITION", _uM([["type-chip", _uM([["property", "all"], ["duration", "0.2s"]])], ["submit-btn", _uM([["property", "transform"], ["duration", "0.2s"]])]])]])]
