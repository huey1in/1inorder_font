
	import { BASE_URL, DEFAULT_FOOD_IMAGE } from '@/common/config.uts'
	
	type OrderItem = {
		id: number,
		quantity: number,
		price: number,
		product_name: string,
		images: Array<string>
	}
	
	type Order = {
		id: number,
		order_number: string,
		status: string,
		total_amount: number,
		items: Array<OrderItem>
	}
	
	const __sfc__ = defineComponent({
		data() {
			return {
				statusTabs: [
					{ label: '全部', value: '' },
					{ label: '已确认', value: 'confirmed' },
					{ label: '制作中', value: 'preparing' },
					{ label: '待取餐', value: 'ready' },
					{ label: '配送中', value: 'delivering' },
					{ label: '已完成', value: 'delivered' },
					{ label: '已取消', value: 'cancelled' }
				],
				currentStatus: '',
				orders: [] as Array<Order>,
				loading: false,
				noMore: false,
				page: 1
			}
		},
		computed: {
			displayOrders(): Array<Order> {
				if (this.currentStatus === '') return this.orders
				if (this.currentStatus === 'pending') {
					return this.orders.filter((o: any) => o.status === 'pending' && o.payment_status !== 'paid')
				}
				return this.orders.filter((o: any) => o.status === this.currentStatus)
			}
		},
		onShow() {
			const status = uni.getStorageSync('orderStatus')
			if (status) {
				this.currentStatus = status
				uni.removeStorageSync('orderStatus')
				this.page = 1
				this.orders = []
				this.noMore = false
			}
			this.fetchOrders()
		},
		methods: {
			getProductImage(item: any): string {
				if (item.images && item.images.length > 0) {
					return item.images[0]
				}
				return DEFAULT_FOOD_IMAGE
			},
			
			fetchOrders() {
				const token = uni.getStorageSync('token')
				if (!token) {
					// 如果未登录，不跳转，显示空状态或提示
					return
				}

				this.loading = true
				
				const requestData: any = {
					page: this.page,
					limit: 10
				}
				if (this.currentStatus !== '') {
					requestData.status = this.currentStatus
				}
				
				uni.request({
					url: `${BASE_URL}/orders/my`,
					method: 'GET',
					header: { 'Authorization': `Bearer ${token}` },
					data: requestData,
					success: (res: any) => {
						if (res.data.success && res.data.data.orders) {
							const newOrders = res.data.data.orders
							if (this.page === 1) {
								this.orders = newOrders
							} else {
								this.orders = [...this.orders, ...newOrders]
							}
							this.noMore = newOrders.length < 10
						}
					},
					complete: () => {
						this.loading = false
					}
				})
			},
			
			switchStatus(status: string) {
				this.currentStatus = status
				this.page = 1
				this.noMore = false
				this.orders = []
				this.fetchOrders()
			},
			
			loadMore() {
				if (this.loading || this.noMore) return
				this.page++
				this.fetchOrders()
			},
			
			getStatusText(order: any): string {
				const map: Record<string, string> = {
					'pending': '待确认',
					'confirmed': '已确认',
					'preparing': '制作中',
					'ready': '待取餐',
					'delivering': '配送中',
					'delivered': '已完成',
					'cancelled': '已取消'
				}
				return map[order?.status] || order?.status || ''
			},

			showDelete(order: any): boolean {
				return order?.status === 'cancelled' || order?.status === 'delivered'
			},
			
			goDetail(id: number) {
				uni.navigateTo({ url: `/pages/order/detail?id=${id}` })
			},
			
			cancelOrder(order: Order) {
				uni.showModal({
					title: '取消订单',
					content: '确定要取消该订单吗？',
					success: (res) => {
						if (res.confirm) {
							const token = uni.getStorageSync('token')
							uni.request({
								url: `${BASE_URL}/orders/${order.id}/cancel`,
								method: 'POST',
								header: { 'Authorization': `Bearer ${token}` },
								success: (res: any) => {
									if (res.data.success) {
										uni.showToast({ title: '已取消', icon: 'success' })
										this.page = 1
										this.fetchOrders()
									}
								}
							})
						}
					}
				})
			},
			
			payOrder(order: Order) {
				uni.showToast({ title: '支付功能开发中', icon: 'none' })
			},
			
			reorder(order: Order) {
				uni.showToast({ title: '已加入购物车', icon: 'success' })
				uni.switchTab({ url: '/pages/cart/cart' })
			},

			deleteOrder(order: Order) {
				uni.showModal({
					title: '删除订单',
					content: '确定删除该订单吗？',
					success: (res) => {
						if (!res.confirm) return
						const token = uni.getStorageSync('token')
						uni.request({
							url: `${BASE_URL}/orders/${order.id}`,
							method: 'DELETE',
							header: { 'Authorization': `Bearer ${token}` },
							success: (resp: any) => {
								if (resp.data?.success) {
									uni.showToast({ title: '已删除', icon: 'success' })
									this.page = 1
									this.orders = []
									this.noMore = false
									this.fetchOrders()
								} else {
									uni.showToast({ title: resp.data?.message || '删除失败', icon: 'none' })
								}
							},
							fail: () => {
								uni.showToast({ title: '网络错误', icon: 'none' })
							}
						})
					}
				})
			}
		}
	})

export default __sfc__
function GenPagesOrderListRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "status-tabs-wrapper" }), [
      _cE("scroll-view", _uM({
        class: "status-tabs",
        direction: "horizontal",
        "scroll-x": true,
        "show-scrollbar": false
      }), [
        _cE("view", _uM({ class: "tabs-inner" }), [
          _cE(Fragment, null, RenderHelpers.renderList(_ctx.statusTabs, (tab, __key, __index, _cached): any => {
            return _cE("view", _uM({
              key: tab.value,
              class: _nC(['tab-item', _ctx.currentStatus === tab.value ? 'active' : '']),
              onClick: () => {_ctx.switchStatus(tab.value)}
            }), [
              _cE("text", _uM({ class: "tab-text" }), _tD(tab.label), 1 /* TEXT */)
            ], 10 /* CLASS, PROPS */, ["onClick"])
          }), 128 /* KEYED_FRAGMENT */)
        ])
      ])
    ]),
    _cE("scroll-view", _uM({
      class: "order-list",
      "scroll-y": "",
      onScrolltolower: _ctx.loadMore
    }), [
      _cE(Fragment, null, RenderHelpers.renderList(_ctx.displayOrders, (order, __key, __index, _cached): any => {
        return _cE("view", _uM({
          class: "order-card",
          key: order.id,
          onClick: () => {_ctx.goDetail(order.id)}
        }), [
          _cE("view", _uM({ class: "order-header" }), [
            _cE("text", _uM({ class: "order-no" }), "订单号: " + _tD(order.order_number), 1 /* TEXT */),
            _cE("text", _uM({
              class: _nC(['order-status', `status-${order.status}`])
            }), _tD(_ctx.getStatusText(order)), 3 /* TEXT, CLASS */)
          ]),
          _cE("view", _uM({ class: "order-items" }), [
            _cE(Fragment, null, RenderHelpers.renderList(order.items, (item, __key, __index, _cached): any => {
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
                  _cE("text", _uM({ class: "item-name" }), _tD(item.product_name || '商品'), 1 /* TEXT */),
                  _cE("text", _uM({ class: "item-qty" }), "x" + _tD(item.quantity), 1 /* TEXT */)
                ]),
                _cE("text", _uM({ class: "item-price" }), "¥" + _tD(item.price), 1 /* TEXT */)
              ])
            }), 128 /* KEYED_FRAGMENT */)
          ]),
          _cE("view", _uM({ class: "order-footer" }), [
            _cE("text", _uM({ class: "order-total" }), [
              "共" + _tD(order.items?.length || 0) + "件商品，合计: ",
              _cE("text", _uM({ class: "total-amount" }), "¥" + _tD(order.total_amount), 1 /* TEXT */)
            ]),
            _cE("view", _uM({ class: "order-actions" }), [
              isTrue(order.status === 'pending' && order.payment_status !== 'paid')
                ? _cE("view", _uM({
                    key: 0,
                    class: "action-btn",
                    onClick: withModifiers(() => {_ctx.cancelOrder(order)}, ["stop"])
                  }), [
                    _cE("text", _uM({ class: "btn-text" }), "取消订单")
                  ], 8 /* PROPS */, ["onClick"])
                : _cC("v-if", true),
              isTrue(order.status === 'pending' && order.payment_status !== 'paid')
                ? _cE("view", _uM({
                    key: 1,
                    class: "action-btn primary",
                    onClick: withModifiers(() => {_ctx.payOrder(order)}, ["stop"])
                  }), [
                    _cE("text", _uM({ class: "btn-text-primary" }), "去支付")
                  ], 8 /* PROPS */, ["onClick"])
                : _cC("v-if", true),
              isTrue(_ctx.showDelete(order))
                ? _cE("view", _uM({
                    key: 2,
                    class: "action-btn danger",
                    onClick: withModifiers(() => {_ctx.deleteOrder(order)}, ["stop"])
                  }), [
                    _cE("text", _uM({ class: "btn-text" }), "删除订单")
                  ], 8 /* PROPS */, ["onClick"])
                : _cC("v-if", true)
            ])
          ])
        ], 8 /* PROPS */, ["onClick"])
      }), 128 /* KEYED_FRAGMENT */),
      isTrue(_ctx.displayOrders.length === 0 && !_ctx.loading)
        ? _cE("view", _uM({
            key: 0,
            class: "empty"
          }), [
            _cE("image", _uM({
              class: "empty-icon",
              src: "/static/icons/icon-order.svg",
              mode: "aspectFit"
            })),
            _cE("text", _uM({ class: "empty-text" }), "暂无订单~")
          ])
        : _cC("v-if", true),
      isTrue(_ctx.loading)
        ? _cE("view", _uM({
            key: 1,
            class: "loading-more"
          }), [
            _cE("text", null, "加载中...")
          ])
        : _cC("v-if", true),
      isTrue(!_ctx.loading && _ctx.noMore && _ctx.orders.length > 0)
        ? _cE("view", _uM({
            key: 2,
            class: "no-more"
          }), [
            _cE("text", null, "没有更多了")
          ])
        : _cC("v-if", true)
    ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower"])
  ])
}
const GenPagesOrderListStyles = [_uM([["container", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#FFF5F8"]]))], ["status-tabs-wrapper", _pS(_uM([["width", "100%"], ["backgroundColor", "rgba(255,255,255,0.9)"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["backdropFilter", "blur(10px)"], ["top", 0], ["zIndex", 10]]))], ["status-tabs", _pS(_uM([["width", "100%"], ["height", "100rpx"]]))], ["tabs-inner", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "nowrap"], ["height", "100rpx"], ["paddingTop", 0], ["paddingRight", "20rpx"], ["paddingBottom", 0], ["paddingLeft", "20rpx"]]))], ["tab-item", _uM([["", _uM([["paddingTop", 0], ["paddingRight", "32rpx"], ["paddingBottom", 0], ["paddingLeft", "32rpx"], ["height", "100rpx"], ["display", "flex"], ["alignItems", "center"], ["justifyContent", "center"], ["position", "relative"]])], [".active::after", _uM([["content", "''"], ["position", "absolute"], ["bottom", 0], ["left", "50%"], ["transform", "translateX(-50%)"], ["width", "40rpx"], ["height", "6rpx"], ["backgroundColor", "#FF69B4"], ["borderTopLeftRadius", "6rpx"], ["borderTopRightRadius", "6rpx"], ["borderBottomRightRadius", "6rpx"], ["borderBottomLeftRadius", "6rpx"]])]])], ["tab-text", _uM([["", _uM([["fontSize", "28rpx"], ["color", "#B8A9C9"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"]])], [".tab-item.active ", _uM([["color", "#FF69B4"], ["fontSize", "30rpx"]])]])], ["order-list", _pS(_uM([["flex", 1], ["paddingTop", "24rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "24rpx"]]))], ["order-card", _pS(_uM([["backgroundColor", "rgba(255,255,255,0.9)"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["marginBottom", "24rpx"], ["overflow", "hidden"], ["boxShadow", "0 6rpx 20rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.6)"], ["borderRightColor", "rgba(255,255,255,0.6)"], ["borderBottomColor", "rgba(255,255,255,0.6)"], ["borderLeftColor", "rgba(255,255,255,0.6)"]]))], ["order-header", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["paddingTop", "24rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "30rpx"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["backgroundColor", "rgba(255,240,245,0.5)"]]))], ["order-no", _pS(_uM([["fontSize", "26rpx"], ["color", "#B8A9C9"], ["fontFamily", "monospace"]]))], ["order-status", _pS(_uM([["fontSize", "26rpx"], ["paddingTop", "6rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "16rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["backgroundColor", "#FFFFFF"]]))], ["status-pending", _pS(_uM([["color", "#FF69B4"], ["backgroundColor", "#FFF0F5"]]))], ["status-confirmed", _pS(_uM([["color", "#4CAF50"], ["backgroundColor", "#E8F5E9"]]))], ["status-preparing", _pS(_uM([["color", "#FF9800"], ["backgroundColor", "#FFF3E0"]]))], ["status-ready", _pS(_uM([["color", "#4CAF50"], ["backgroundColor", "#E8F5E9"]]))], ["status-delivering", _pS(_uM([["color", "#2196F3"], ["backgroundColor", "#E3F2FD"]]))], ["status-delivered", _pS(_uM([["color", "#B8A9C9"], ["backgroundColor", "#F3E5F5"]]))], ["status-cancelled", _pS(_uM([["color", "#D4C4E3"], ["backgroundColor", "#F5F5F5"]]))], ["order-items", _pS(_uM([["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "30rpx"]]))], ["order-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "16rpx"], ["paddingRight", 0], ["paddingBottom", "16rpx"], ["paddingLeft", 0]]))], ["item-image", _pS(_uM([["width", "120rpx"], ["height", "120rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["borderTopWidth", "4rpx"], ["borderRightWidth", "4rpx"], ["borderBottomWidth", "4rpx"], ["borderLeftWidth", "4rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFF0F5"], ["borderRightColor", "#FFF0F5"], ["borderBottomColor", "#FFF0F5"], ["borderLeftColor", "#FFF0F5"], ["boxShadow", "0 4rpx 12rpx rgba(0,0,0,0.05)"]]))], ["item-info", _pS(_uM([["flex", 1], ["marginLeft", "24rpx"]]))], ["item-name", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["item-qty", _pS(_uM([["fontSize", "26rpx"], ["color", "#B8A9C9"], ["marginTop", "10rpx"]]))], ["item-price", _pS(_uM([["fontSize", "30rpx"], ["color", "#FF69B4"]]))], ["order-footer", _pS(_uM([["paddingTop", "24rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "30rpx"], ["borderTopWidth", 1], ["borderTopStyle", "solid"], ["borderTopColor", "rgba(255,228,236,0.6)"], ["display", "flex"], ["flexDirection", "column"], ["alignItems", "flex-end"]]))], ["order-total", _pS(_uM([["fontSize", "28rpx"], ["color", "#B8A9C9"]]))], ["total-amount", _pS(_uM([["color", "#FF69B4"], ["fontSize", "34rpx"], ["marginLeft", "10rpx"]]))], ["order-actions", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "flex-end"], ["marginTop", "24rpx"]]))], ["action-btn", _uM([["", _uM([["paddingTop", "16rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "16rpx"], ["paddingLeft", "36rpx"], ["borderTopWidth", 2], ["borderRightWidth", 2], ["borderBottomWidth", 2], ["borderLeftWidth", 2], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["marginLeft", "20rpx"], ["backgroundColor", "#FFF5F8"], ["transitionProperty", "all"], ["transitionDuration", "0.2s"], ["backgroundColor:active", "#FFE4EC"]])], [".primary", _uM([["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopColor", "#FF69B4"], ["borderRightColor", "#FF69B4"], ["borderBottomColor", "#FF69B4"], ["borderLeftColor", "#FF69B4"], ["boxShadow", "0 4rpx 12rpx rgba(255, 105, 180, 0.3)"]])], [".primary:active", _uM([["transform", "scale(0.95)"]])]])], ["btn-text", _pS(_uM([["fontSize", "28rpx"], ["color", "#FF69B4"]]))], ["btn-text-primary", _pS(_uM([["fontSize", "28rpx"], ["color", "#ffffff"]]))], ["empty", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["paddingTop", "120rpx"], ["paddingRight", 0], ["paddingBottom", "120rpx"], ["paddingLeft", 0]]))], ["empty-icon", _pS(_uM([["width", "200rpx"], ["height", "200rpx"], ["opacity", 0.9], ["filter", "drop-shadow(0 10rpx 20rpx rgba(255, 105, 180, 0.2))"], ["animation", "float 3s ease-in-out infinite"]]))], ["empty-text", _pS(_uM([["fontSize", "30rpx"], ["color", "#B8A9C9"], ["marginTop", "30rpx"]]))], ["loading-more", _pS(_uM([["textAlign", "center"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["color", "#B8A9C9"], ["fontSize", "26rpx"]]))], ["no-more", _pS(_uM([["textAlign", "center"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["color", "#B8A9C9"], ["fontSize", "26rpx"]]))], ["@FONT-FACE", _uM([["0", _uM([])]])], ["@TRANSITION", _uM([["tab-text", _uM([["property", "all"], ["duration", "0.3s"]])], ["action-btn", _uM([["property", "all"], ["duration", "0.2s"]])]])]])]
