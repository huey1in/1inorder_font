
	// 导入API配置
	import { BASE_URL, DEFAULT_FOOD_IMAGE } from '@/common/config.uts'
	
	const __sfc__ = defineComponent({
		data() {
			return {
				shop: {
					name: '',
					logo: '',
					description: '',
					opening_hours: '',
					is_open: false,
					announcement: '',
					address: '',
					phone: '',
					min_order_amount: 0,
					delivery_fee: 0,
					delivery_start_amount: 0
			},
				categories: [] as Array<{id: number, name: string}>,
				products: [] as Array<{id: number, name: string, description: string, price: number, original_price: number, images: Array<string>}>,
				currentCategory: 0,
				cartCount: 0,
				cartTotal: 0,
				cartItems: [] as Array<{id: number, product_id: number, name: string, price: number, quantity: number, images: Array<string>}>,
				showCartPopup: false,
				loading: false,
				noMore: false,
				page: 1,
				limit: 10
			}
		},
		onLoad() {
			this.fetchShopInfo()
			this.fetchCategories()
			this.fetchCartCount()
		},
		onShow() {
			this.fetchCartCount()
		},
		methods: {
			// 获取店铺信息
			fetchShopInfo() {
				uni.request({
					url: `${BASE_URL}/shop/info`,
					method: 'GET',
					success: (res: any) => {
						if (res.data.success && res.data.data.shop) {
							const info = res.data.data.shop
							this.shop = {
								...this.shop,
								...info,
								is_open: (info.is_currently_open !== undefined ? info.is_currently_open : info.is_open) ? true : false
							}
						}
					},
					fail: (err) => {
						console.log('获取店铺信息失败', err)
					}
				})
			},
			
			// 获取分类列表
			fetchCategories() {
				uni.request({
					url: `${BASE_URL}/categories`,
					method: 'GET',
					success: (res: any) => {
						if (res.data.success && res.data.data.categories) {
							this.categories = res.data.data.categories
							if (this.categories.length > 0) {
								this.fetchProducts()
							}
						}
					},
					fail: (err) => {
						console.log('获取分类失败', err)
					}
				})
			},
			
			// 获取商品列表
			fetchProducts() {
				if (this.loading) return
				this.loading = true
				
				const categoryId = this.categories[this.currentCategory]?.id
				uni.request({
					url: `${BASE_URL}/products`,
					method: 'GET',
					data: {
						category_id: categoryId,
						page: this.page,
						limit: this.limit
					},
					success: (res: any) => {
						if (res.data.success && res.data.data.products) {
							const newProducts = res.data.data.products
							if (this.page === 1) {
								this.products = newProducts
							} else {
								this.products = [...this.products, ...newProducts]
							}
							this.noMore = newProducts.length < this.limit
						}
					},
					fail: (err) => {
						console.log('获取商品失败', err)
					},
					complete: () => {
						this.loading = false
					}
				})
			},
			
			// 获取购物车数量
			fetchCartCount() {
				const token = uni.getStorageSync('token')
				if (!token) return
				
				uni.request({
					url: `${BASE_URL}/cart/count`,
					method: 'GET',
					header: {
						'Authorization': `Bearer ${token}`
					},
					success: (res: any) => {
						if (res.data.success) {
							this.cartCount = res.data.data.count || 0
						}
					}
				})
				
				// 同时获取购物车总金额
				uni.request({
					url: `${BASE_URL}/cart`,
					method: 'GET',
					header: {
						'Authorization': `Bearer ${token}`
					},
					success: (res: any) => {
						if (res.data.success && res.data.data.cart) {
							this.cartTotal = res.data.data.cart.total_amount || 0
							// 同时更新购物车列表
							const items = res.data.data.cart.items || []
							this.cartItems = items.map((item: any) => ({
								id: item.cart_item_id,
								product_id: item.product_id,
								name: item.name,
								price: item.price,
								quantity: item.quantity,
								images: item.images || []
							}))
						}
					}
				})
			},
			
			// 切换分类
			selectCategory(index: number) {
				this.currentCategory = index
				this.page = 1
				this.noMore = false
				this.products = []
				this.fetchProducts()
			},
			
			// 加载更多
			loadMore() {
				if (this.noMore || this.loading) return
				this.page++
				this.fetchProducts()
			},
			
			// 添加到购物车
			addToCart(product: any) {
				const token = uni.getStorageSync('token')
				if (!token) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					uni.navigateTo({ url: '/pages/login/login' })
					return
				}
				
				uni.request({
					url: `${BASE_URL}/cart/add`,
					method: 'POST',
					header: {
						'Authorization': `Bearer ${token}`,
						'Content-Type': 'application/json'
					},
					data: {
						product_id: product.id,
						quantity: 1
					},
					success: (res: any) => {
						if (res.data.success) {
							uni.showToast({ title: '已加入购物车', icon: 'success' })
							this.fetchCartCount()
						} else {
							uni.showToast({ title: res.data.message || '添加失败', icon: 'none' })
						}
					},
					fail: () => {
						uni.showToast({ title: '网络错误', icon: 'none' })
					}
				})
			},
			
			// 跳转到商品详情
			goToDetail(id: number) {
				uni.navigateTo({ url: `/pages/product/detail?id=${id}` })
			},
			
			// 获取商品图片
			getProductImage(product: any): string {
				if (product.images && product.images.length > 0) {
					return product.images[0]
				}
				return DEFAULT_FOOD_IMAGE
			},
			
			// 获取购物车商品图片
			getCartItemImage(item: any): string {
				if (item.images && item.images.length > 0) {
					return item.images[0]
				}
				return DEFAULT_FOOD_IMAGE
			},
			
			// 切换购物车弹窗
			toggleCartPopup() {
				this.showCartPopup = !this.showCartPopup
				if (this.showCartPopup) {
					this.fetchCartCount()
				}
			},
			
			// 修改购物车商品数量
			changeQuantity(item: any, delta: number) {
				const newQty = item.quantity + delta
				if (newQty < 1) {
					// 删除商品
					this.removeCartItem(item)
					return
				}
				
				const token = uni.getStorageSync('token')
				uni.request({
					url: `${BASE_URL}/cart/item/${item.id}`,
					method: 'PATCH',
					header: {
						'Authorization': `Bearer ${token}`,
						'Content-Type': 'application/json'
					},
					data: {
						quantity: newQty
					},
					success: (res: any) => {
						if (res.data.success) {
							item.quantity = newQty
							this.fetchCartCount()
						}
					}
				})
			},
			
			// 删除购物车商品
			removeCartItem(item: any) {
				const token = uni.getStorageSync('token')
				uni.request({
					url: `${BASE_URL}/cart/item/${item.id}`,
					method: 'DELETE',
					header: {
						'Authorization': `Bearer ${token}`,
						'Content-Type': 'application/json'
					},
					success: (res: any) => {
						if (res.data.success) {
							const index = this.cartItems.findIndex((i: any) => i.id === item.id)
							if (index > -1) {
								this.cartItems.splice(index, 1)
							}
							this.fetchCartCount()
							// 如果购物车为空，关闭弹窗
							if (this.cartItems.length === 0) {
								this.showCartPopup = false
							}
						}
					}
				})
			},
			
			// 清空购物车
			clearCart() {
				uni.showModal({
					title: '确认清空',
					content: '确定要清空购物车吗？',
					success: (res) => {
						if (res.confirm) {
							const token = uni.getStorageSync('token')
							uni.request({
								url: `${BASE_URL}/cart/clear`,
								method: 'DELETE',
								header: {
									'Authorization': `Bearer ${token}`
								},
								success: (res: any) => {
									if (res.data.success) {
										this.cartItems = []
										this.cartCount = 0
										this.cartTotal = 0
										this.showCartPopup = false
									}
								}
							})
						}
					}
				})
			},
			
			// 去结算
			goToCheckout() {
				this.showCartPopup = false
				uni.navigateTo({ url: '/pages/order/create' })
			},
			
			// 图片加载失败处理
			handleImageError(item: any) {
				// 强制更新图片为默认图
				// 注意：这里直接修改数据源，确保视图更新
				if (item.images) {
					item.images = [DEFAULT_FOOD_IMAGE]
				}
			}
		}
	})

export default __sfc__
function GenPagesIndexIndexRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "shop-header" }), [
      _cE("view", _uM({ class: "header-bg-circle circle-1" })),
      _cE("view", _uM({ class: "header-bg-circle circle-2" })),
      _cE("view", _uM({ class: "shop-content" }), [
        _cE("view", _uM({ class: "shop-info" }), [
          _cE("text", _uM({ class: "shop-name" }), _tD(_ctx.shop.name), 1 /* TEXT */),
          _cE("text", _uM({ class: "shop-desc" }), _tD(_ctx.shop.description), 1 /* TEXT */),
          _cE("view", _uM({ class: "shop-status-row" }), [
            _cE("view", _uM({
              class: _nC(['status-badge', _ctx.shop.is_open ? 'open' : 'closed'])
            }), [
              _cE("view", _uM({ class: "status-dot" })),
              _cE("text", _uM({ class: "status-text" }), _tD(_ctx.shop.is_open ? '营业中' : '休息中'), 1 /* TEXT */)
            ], 2 /* CLASS */),
            _cE("text", _uM({ class: "opening-hours" }), _tD(_ctx.shop.opening_hours), 1 /* TEXT */)
          ])
        ])
      ]),
      _cE("view", _uM({ class: "shop-meta" }), [
        isTrue(_ctx.shop.address)
          ? _cE("view", _uM({
              key: 0,
              class: "meta-row"
            }), [
              _cE("image", _uM({
                class: "meta-icon",
                src: "/static/icons/icon-address.svg",
                mode: "aspectFit"
              })),
              _cE("text", _uM({ class: "meta-text" }), _tD(_ctx.shop.address), 1 /* TEXT */)
            ])
          : _cC("v-if", true),
        isTrue(_ctx.shop.phone)
          ? _cE("view", _uM({
              key: 1,
              class: "meta-row"
            }), [
              _cE("image", _uM({
                class: "meta-icon",
                src: "/static/icons/icon-phone.svg",
                mode: "aspectFit"
              })),
              _cE("text", _uM({ class: "meta-text" }), _tD(_ctx.shop.phone), 1 /* TEXT */)
            ])
          : _cC("v-if", true),
        _cE("view", _uM({ class: "meta-tags" }), [
          _cE("text", _uM({ class: "meta-tag" }), "起送￥" + _tD(_ctx.shop.delivery_start_amount || 0), 1 /* TEXT */),
          _cE("text", _uM({ class: "meta-tag" }), "配送费￥" + _tD(_ctx.shop.delivery_fee || 0), 1 /* TEXT */),
          _cE("text", _uM({ class: "meta-tag" }), "起订￥" + _tD(_ctx.shop.min_order_amount || 0), 1 /* TEXT */)
        ])
      ])
    ]),
    isTrue(_ctx.shop.announcement)
      ? _cE("view", _uM({
          key: 0,
          class: "announcement"
        }), [
          _cE("image", _uM({
            class: "announcement-icon",
            src: "/static/icons/icon-notice.svg",
            mode: "aspectFit"
          })),
          _cE("text", _uM({ class: "announcement-text" }), _tD(_ctx.shop.announcement), 1 /* TEXT */)
        ])
      : _cC("v-if", true),
    _cE("view", _uM({ class: "main-content" }), [
      _cE("scroll-view", _uM({
        class: "category-list",
        "scroll-y": ""
      }), [
        _cE(Fragment, null, RenderHelpers.renderList(_ctx.categories, (item, index, __index, _cached): any => {
          return _cE("view", _uM({
            key: item.id,
            class: _nC(['category-item', _ctx.currentCategory === index ? 'active' : '']),
            onClick: () => {_ctx.selectCategory(index)}
          }), [
            _cE("text", _uM({ class: "category-name" }), _tD(item.name), 1 /* TEXT */)
          ], 10 /* CLASS, PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */)
      ]),
      _cE("scroll-view", _uM({
        class: "product-list",
        "scroll-y": "",
        onScrolltolower: _ctx.loadMore
      }), [
        _cE(Fragment, null, RenderHelpers.renderList(_ctx.products, (product, __key, __index, _cached): any => {
          return _cE("view", _uM({
            class: "product-item",
            key: product.id,
            onClick: () => {_ctx.goToDetail(product.id)}
          }), [
            _cE("image", _uM({
              class: "product-image",
              src: _ctx.getProductImage(product),
              mode: "aspectFill",
              onError: () => {_ctx.handleImageError(product)}
            }), null, 40 /* PROPS, NEED_HYDRATION */, ["src", "onError"]),
            _cE("view", _uM({ class: "product-info" }), [
              _cE("text", _uM({ class: "product-name" }), _tD(product.name), 1 /* TEXT */),
              _cE("text", _uM({ class: "product-desc" }), _tD(product.description), 1 /* TEXT */),
              _cE("view", _uM({ class: "product-bottom" }), [
                _cE("view", _uM({ class: "price-box" }), [
                  _cE("text", _uM({ class: "price" }), "¥" + _tD(product.price), 1 /* TEXT */),
                  isTrue(product.original_price)
                    ? _cE("text", _uM({
                        key: 0,
                        class: "original-price"
                      }), "¥" + _tD(product.original_price), 1 /* TEXT */)
                    : _cC("v-if", true)
                ]),
                _cE("view", _uM({
                  class: "cart-btn",
                  onClick: withModifiers(() => {_ctx.addToCart(product)}, ["stop"])
                }), [
                  _cE("text", _uM({ class: "cart-btn-text" }), "+")
                ], 8 /* PROPS */, ["onClick"])
              ])
            ])
          ], 8 /* PROPS */, ["onClick"])
        }), 128 /* KEYED_FRAGMENT */),
        isTrue(_ctx.loading)
          ? _cE("view", _uM({
              key: 0,
              class: "loading-more"
            }), [
              _cE("text", null, "加载中...")
            ])
          : _cC("v-if", true),
        isTrue(!_ctx.loading && _ctx.noMore)
          ? _cE("view", _uM({
              key: 1,
              class: "no-more"
            }), [
              _cE("text", null, "没有更多了")
            ])
          : _cC("v-if", true)
      ], 40 /* PROPS, NEED_HYDRATION */, ["onScrolltolower"])
    ]),
    isTrue(_ctx.showCartPopup)
      ? _cE("view", _uM({
          key: 1,
          class: "cart-overlay",
          onClick: _ctx.toggleCartPopup
        }), null, 8 /* PROPS */, ["onClick"])
      : _cC("v-if", true),
    _ctx.cartCount > 0
      ? _cE("view", _uM({
          key: 2,
          class: "cart-container"
        }), [
          _cE("view", _uM({
            class: _nC(['cart-expand-list', _ctx.showCartPopup ? 'show' : ''])
          }), [
            _cE("view", _uM({ class: "cart-expand-header" }), [
              _cE("text", _uM({ class: "cart-expand-title" }), "♡ 已选商品 ♡"),
              _cE("view", _uM({
                class: "cart-clear-btn",
                onClick: _ctx.clearCart
              }), [
                _cE("text", _uM({ class: "cart-clear-text" }), "清空")
              ], 8 /* PROPS */, ["onClick"])
            ]),
            _cE("scroll-view", _uM({
              class: "cart-expand-scroll",
              "scroll-y": ""
            }), [
              _cE(Fragment, null, RenderHelpers.renderList(_ctx.cartItems, (item, __key, __index, _cached): any => {
                return _cE("view", _uM({
                  class: "cart-expand-item",
                  key: item.id
                }), [
                  _cE("image", _uM({
                    class: "cart-expand-img",
                    src: _ctx.getCartItemImage(item),
                    mode: "aspectFill",
                    onError: () => {_ctx.handleImageError(item)}
                  }), null, 40 /* PROPS, NEED_HYDRATION */, ["src", "onError"]),
                  _cE("view", _uM({ class: "cart-expand-info" }), [
                    _cE("text", _uM({ class: "cart-expand-name" }), _tD(item.name), 1 /* TEXT */),
                    _cE("text", _uM({ class: "cart-expand-price" }), "¥" + _tD(item.price), 1 /* TEXT */)
                  ]),
                  _cE("view", _uM({ class: "cart-expand-qty" }), [
                    _cE("view", _uM({
                      class: "cart-qty-btn",
                      onClick: () => {_ctx.changeQuantity(item, -1)}
                    }), [
                      _cE("text", _uM({ class: "cart-qty-text" }), "-")
                    ], 8 /* PROPS */, ["onClick"]),
                    _cE("text", _uM({ class: "cart-qty-num" }), _tD(item.quantity), 1 /* TEXT */),
                    _cE("view", _uM({
                      class: "cart-qty-btn",
                      onClick: () => {_ctx.changeQuantity(item, 1)}
                    }), [
                      _cE("text", _uM({ class: "cart-qty-text" }), "+")
                    ], 8 /* PROPS */, ["onClick"])
                  ])
                ])
              }), 128 /* KEYED_FRAGMENT */)
            ])
          ], 2 /* CLASS */),
          _cE("view", _uM({
            class: _nC(['cart-footer', _ctx.showCartPopup ? 'expanded' : ''])
          }), [
            _cE("view", _uM({
              class: "cart-footer-left",
              onClick: _ctx.toggleCartPopup
            }), [
              _cE("view", _uM({ class: "cart-icon-box" }), [
                _cE("image", _uM({
                  class: "cart-footer-icon",
                  src: "/static/icons/icon-cart.svg",
                  mode: "aspectFit"
                })),
                _cE("view", _uM({ class: "cart-count-badge" }), [
                  _cE("text", _uM({ class: "cart-count-text" }), _tD(_ctx.cartCount), 1 /* TEXT */)
                ])
              ]),
              _cE("text", _uM({ class: "cart-footer-total" }), "¥" + _tD(_ctx.cartTotal.toFixed(2)), 1 /* TEXT */)
            ], 8 /* PROPS */, ["onClick"]),
            _cE("view", _uM({
              class: "cart-footer-btn",
              onClick: _ctx.goToCheckout
            }), [
              _cE("text", _uM({ class: "cart-footer-btn-text" }), "去结算 ♡")
            ], 8 /* PROPS */, ["onClick"])
          ], 2 /* CLASS */)
        ])
      : _cC("v-if", true)
  ])
}
const GenPagesIndexIndexStyles = [_uM([["container", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#FFF5F8"]]))], ["shop-header", _pS(_uM([["position", "relative"], ["paddingTop", "40rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["backgroundImage", "linear-gradient(135deg, #FF9A9E 0%, #FECFEF 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["boxShadow", "0 10rpx 30rpx rgba(255, 105, 180, 0.2)"], ["overflow", "hidden"]]))], ["header-bg-circle", _pS(_uM([["position", "absolute"], ["backgroundImage", "none"], ["backgroundColor", "rgba(255,255,255,0.15)"], ["pointerEvents", "none"]]))], ["circle-1", _pS(_uM([["width", "300rpx"], ["height", "300rpx"], ["top", "-100rpx"], ["right", "-50rpx"]]))], ["circle-2", _pS(_uM([["width", "200rpx"], ["height", "200rpx"], ["bottom", "-50rpx"], ["left", "-50rpx"]]))], ["shop-content", _pS(_uM([["position", "relative"], ["zIndex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["shop-info", _pS(_uM([["flex", 1], ["display", "flex"], ["flexDirection", "column"]]))], ["shop-name", _pS(_uM([["fontSize", "44rpx"], ["color", "#FFFFFF"], ["letterSpacing", 2], ["textShadow", "0 2rpx 4rpx rgba(0,0,0,0.1)"], ["marginBottom", "8rpx"]]))], ["shop-desc", _pS(_uM([["fontSize", "26rpx"], ["color", "rgba(255,255,255,0.95)"], ["marginBottom", "16rpx"]]))], ["shop-status-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"]]))], ["status-badge", _uM([["", _uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "6rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "16rpx"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["backdropFilter", "blur(5px)"], ["marginRight", "16rpx"]])], [".open", _uM([["backgroundImage", "none"], ["backgroundColor", "rgba(152,216,170,0.9)"]])], [".closed", _uM([["backgroundImage", "none"], ["backgroundColor", "rgba(255,107,107,0.9)"]])]])], ["status-dot", _pS(_uM([["width", "12rpx"], ["height", "12rpx"], ["backgroundColor", "#ffffff"], ["marginRight", "8rpx"]]))], ["status-text", _pS(_uM([["fontSize", "22rpx"], ["color", "#FFFFFF"], ["fontWeight", "700"]]))], ["opening-hours", _pS(_uM([["fontSize", "24rpx"], ["color", "rgba(255,255,255,0.9)"]]))], ["shop-meta", _pS(_uM([["position", "relative"], ["zIndex", 1], ["marginTop", "20rpx"], ["backgroundImage", "none"], ["backgroundColor", "rgba(255,255,255,0.18)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.3)"], ["borderRightColor", "rgba(255,255,255,0.3)"], ["borderBottomColor", "rgba(255,255,255,0.3)"], ["borderLeftColor", "rgba(255,255,255,0.3)"], ["borderTopLeftRadius", "24rpx"], ["borderTopRightRadius", "24rpx"], ["borderBottomRightRadius", "24rpx"], ["borderBottomLeftRadius", "24rpx"], ["paddingTop", "16rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "16rpx"], ["paddingLeft", "20rpx"], ["backdropFilter", "blur(8px)"]]))], ["meta-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", "10rpx"]]))], ["meta-icon", _pS(_uM([["width", "28rpx"], ["height", "28rpx"], ["marginRight", "12rpx"]]))], ["meta-text", _pS(_uM([["fontSize", "26rpx"], ["color", "#FFFFFF"], ["opacity", 0.95]]))], ["meta-tags", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["flexWrap", "wrap"], ["gap", "10rpx"], ["marginTop", "8rpx"]]))], ["meta-tag", _pS(_uM([["fontSize", "24rpx"], ["color", "#FF69B4"], ["backgroundImage", "none"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["paddingTop", "6rpx"], ["paddingRight", "14rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "14rpx"], ["fontWeight", "700"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,105,180,0.2)"], ["borderRightColor", "rgba(255,105,180,0.2)"], ["borderBottomColor", "rgba(255,105,180,0.2)"], ["borderLeftColor", "rgba(255,105,180,0.2)"]]))], ["announcement", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "30rpx"], ["marginTop", "24rpx"], ["marginRight", "24rpx"], ["marginBottom", "24rpx"], ["marginLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.8)"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["borderTopWidth", 2], ["borderRightWidth", 2], ["borderBottomWidth", 2], ["borderLeftWidth", 2], ["borderTopStyle", "dashed"], ["borderRightStyle", "dashed"], ["borderBottomStyle", "dashed"], ["borderLeftStyle", "dashed"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["boxShadow", "0 4rpx 16rpx rgba(255, 105, 180, 0.05)"], ["backdropFilter", "blur(10px)"]]))], ["announcement-icon", _pS(_uM([["width", "44rpx"], ["height", "44rpx"], ["marginRight", "16rpx"]]))], ["announcement-text", _pS(_uM([["fontSize", "26rpx"], ["color", "#5D4E6D"], ["flex", 1]]))], ["main-content", _pS(_uM([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["overflow", "hidden"], ["marginTop", 0], ["marginRight", "24rpx"], ["marginBottom", 0], ["marginLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.9)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 8rpx 30rpx rgba(255, 105, 180, 0.15)"], ["backdropFilter", "blur(20px)"]]))], ["category-list", _pS(_uM([["width", "190rpx"], ["backgroundColor", "#FFF0F5"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", 0], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", "40rpx"]]))], ["category-item", _uM([["", _uM([["paddingTop", "36rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "36rpx"], ["paddingLeft", "20rpx"], ["textAlign", "center"], ["position", "relative"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"], ["transitionTimingFunction", "cubic-bezier(0.4,0,0.2,1)"]])], [".active", _uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", 0], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", "30rpx"]])], [".active::before", _uM([["content", "''"], ["position", "absolute"], ["left", 0], ["top", "50%"], ["transform", "translateY(-50%)"], ["width", "8rpx"], ["height", "40rpx"], ["backgroundColor", "#FF69B4"], ["borderTopLeftRadius", 0], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", 0]])]])], ["category-name", _uM([["", _uM([["fontSize", "28rpx"], ["color", "#B8A9C9"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"]])], [".category-item.active ", _uM([["color", "#FF69B4"], ["fontSize", "30rpx"], ["transform", "scale(1.05)"]])]])], ["product-list", _pS(_uM([["flex", 1], ["backgroundColor", "#FFFFFF"], ["paddingTop", 0], ["paddingRight", "24rpx"], ["paddingBottom", 0], ["paddingLeft", "24rpx"], ["borderTopLeftRadius", 0], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", 0]]))], ["product-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["paddingTop", "30rpx"], ["paddingRight", 0], ["paddingBottom", "30rpx"], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["borderBottomWidth:last-child", "medium"], ["borderBottomStyle:last-child", "none"], ["borderBottomColor:last-child", "#000000"]]))], ["product-image", _pS(_uM([["width", "180rpx"], ["height", "180rpx"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["borderTopWidth", "4rpx"], ["borderRightWidth", "4rpx"], ["borderBottomWidth", "4rpx"], ["borderLeftWidth", "4rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFF0F5"], ["borderRightColor", "#FFF0F5"], ["borderBottomColor", "#FFF0F5"], ["borderLeftColor", "#FFF0F5"], ["boxShadow", "0 4rpx 12rpx rgba(0,0,0,0.05)"]]))], ["product-info", _pS(_uM([["flex", 1], ["marginLeft", "24rpx"], ["display", "flex"], ["flexDirection", "column"], ["justifyContent", "space-between"], ["paddingTop", "4rpx"], ["paddingRight", 0], ["paddingBottom", "4rpx"], ["paddingLeft", 0]]))], ["product-name", _pS(_uM([["fontSize", "32rpx"], ["fontWeight", "700"], ["color", "#5D4E6D"]]))], ["product-desc", _pS(_uM([["fontSize", "24rpx"], ["color", "#B8A9C9"], ["marginTop", "10rpx"], ["lineHeight", 1.4], ["overflow", "hidden"], ["textOverflow", "ellipsis"], ["WebkitLineClamp", 2], ["WebkitBoxOrient", "vertical"]]))], ["product-bottom", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["marginTop", "16rpx"]]))], ["price-box", _pS(_uM([["display", "flex"], ["flexDirection", "row"]]))], ["price", _pS(_uM([["fontSize", "36rpx"], ["color", "#FF69B4"]]))], ["original-price", _pS(_uM([["fontSize", "24rpx"], ["color", "#D4C4E3"], ["textDecoration", "line-through"], ["marginLeft", "12rpx"]]))], ["cart-btn", _pS(_uM([["width", "64rpx"], ["height", "64rpx"], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["boxShadow", "0 6rpx 16rpx rgba(255, 105, 180, 0.3)"], ["transitionProperty", "transform"], ["transitionDuration", "0.2s"], ["transform:active", "scale(0.9)"]]))], ["cart-btn-text", _pS(_uM([["fontSize", "40rpx"], ["color", "#ffffff"], ["fontWeight", "bold"], ["lineHeight", 1], ["marginTop", "-4rpx"]]))], ["loading-more", _pS(_uM([["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["textAlign", "center"], ["color", "#B8A9C9"], ["fontSize", "26rpx"]]))], ["no-more", _pS(_uM([["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["textAlign", "center"], ["color", "#B8A9C9"], ["fontSize", "26rpx"]]))], ["cart-overlay", _pS(_uM([["position", "fixed"], ["top", 0], ["left", 0], ["right", 0], ["bottom", 0], ["backgroundColor", "rgba(0,0,0,0.4)"], ["zIndex", 998]]))], ["cart-container", _pS(_uM([["position", "fixed"], ["left", "24rpx"], ["right", "24rpx"], ["bottom", "20rpx"], ["zIndex", 999], ["display", "flex"], ["flexDirection", "column"]]))], ["cart-expand-list", _uM([["", _uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["maxHeight", 0], ["opacity", 0], ["transform", "translateY(20rpx)"], ["display", "flex"], ["flexDirection", "column"], ["boxShadow", "0 -5rpx 20rpx rgba(255, 105, 180, 0.1)"], ["overflow", "hidden"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"], ["transitionTimingFunction", "cubic-bezier(0.4,0,0.2,1)"]])], [".show", _uM([["opacity", 1], ["transform", "translateY(0)"]])]])], ["cart-expand-header", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "space-between"], ["alignItems", "center"], ["paddingTop", "30rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "36rpx"], ["backgroundColor", "#FFF5F8"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", 0], ["borderBottomLeftRadius", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#FFE4EC"]]))], ["cart-expand-title", _pS(_uM([["fontSize", "32rpx"], ["color", "#FF69B4"]]))], ["cart-clear-btn", _pS(_uM([["paddingTop", "12rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "12rpx"], ["paddingLeft", "24rpx"], ["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"]]))], ["cart-clear-text", _pS(_uM([["fontSize", "26rpx"], ["color", "#B8A9C9"]]))], ["cart-expand-scroll", _pS(_uM([["paddingTop", 0], ["paddingRight", "36rpx"], ["paddingBottom", 0], ["paddingLeft", "36rpx"], ["backgroundColor", "#FFFFFF"]]))], ["cart-expand-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "24rpx"], ["paddingRight", 0], ["paddingBottom", "24rpx"], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#FFE4EC"]]))], ["cart-expand-img", _pS(_uM([["width", "100rpx"], ["height", "100rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["borderTopWidth", "3rpx"], ["borderRightWidth", "3rpx"], ["borderBottomWidth", "3rpx"], ["borderLeftWidth", "3rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFF0F5"], ["borderRightColor", "#FFF0F5"], ["borderBottomColor", "#FFF0F5"], ["borderLeftColor", "#FFF0F5"]]))], ["cart-expand-info", _pS(_uM([["flex", 1], ["marginLeft", "20rpx"], ["display", "flex"], ["flexDirection", "column"]]))], ["cart-expand-name", _pS(_uM([["fontSize", "28rpx"], ["color", "#5D4E6D"], ["marginBottom", "8rpx"]]))], ["cart-expand-price", _pS(_uM([["fontSize", "30rpx"], ["color", "#FF69B4"]]))], ["cart-expand-qty", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["backgroundColor", "#FFF0F5"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["paddingTop", "6rpx"], ["paddingRight", "6rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "6rpx"]]))], ["cart-qty-btn", _pS(_uM([["width", "52rpx"], ["height", "52rpx"], ["backgroundColor", "#FFFFFF"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["backgroundColor:active", "#FFE4EC"]]))], ["cart-qty-text", _pS(_uM([["fontSize", "32rpx"], ["color", "#FF69B4"], ["fontWeight", "bold"]]))], ["cart-qty-num", _pS(_uM([["fontSize", "28rpx"], ["color", "#5D4E6D"], ["paddingTop", 0], ["paddingRight", "20rpx"], ["paddingBottom", 0], ["paddingLeft", "20rpx"], ["fontWeight", "bold"]]))], ["cart-footer", _uM([["", _uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "30rpx"], ["backgroundColor", "rgba(255,255,255,0.98)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 4rpx 16rpx rgba(255, 105, 180, 0.15)"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"], ["position", "relative"], ["zIndex", 10], ["overflow", "visible"]])], [".expanded", _uM([["borderTopLeftRadius", 0], ["borderTopRightRadius", 0], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 4rpx 16rpx rgba(255, 105, 180, 0.15)"]])]])], ["cart-footer-left", _pS(_uM([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["overflow", "visible"]]))], ["cart-icon-box", _pS(_uM([["position", "relative"], ["width", "90rpx"], ["height", "90rpx"], ["backgroundColor", "#FFF0F5"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["overflow", "visible"]]))], ["cart-footer-icon", _pS(_uM([["width", "48rpx"], ["height", "48rpx"]]))], ["cart-count-badge", _pS(_uM([["position", "absolute"], ["top", "-8rpx"], ["right", "-8rpx"], ["minWidth", "40rpx"], ["height", "40rpx"], ["backgroundImage", "linear-gradient(135deg, #FF69B4 0%, #FF1493 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["paddingTop", 0], ["paddingRight", "10rpx"], ["paddingBottom", 0], ["paddingLeft", "10rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"], ["borderTopWidth", "3rpx"], ["borderRightWidth", "3rpx"], ["borderBottomWidth", "3rpx"], ["borderLeftWidth", "3rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFFFFF"], ["borderRightColor", "#FFFFFF"], ["borderBottomColor", "#FFFFFF"], ["borderLeftColor", "#FFFFFF"], ["zIndex", 100], ["boxShadow", "0 2rpx 8rpx rgba(255, 20, 147, 0.3)"]]))], ["cart-count-text", _pS(_uM([["fontSize", "24rpx"], ["color", "#FFFFFF"], ["fontWeight", "bold"]]))], ["cart-footer-total", _pS(_uM([["fontSize", "40rpx"], ["color", "#5D4E6D"], ["marginLeft", "20rpx"]]))], ["cart-footer-btn", _pS(_uM([["paddingTop", "24rpx"], ["paddingRight", "50rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "50rpx"], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["boxShadow", "0 6rpx 20rpx rgba(255, 105, 180, 0.4)"], ["opacity:active", 0.9]]))], ["cart-footer-btn-text", _pS(_uM([["fontSize", "32rpx"], ["color", "#FFFFFF"], ["fontWeight", "bold"], ["letterSpacing", 1]]))], ["@TRANSITION", _uM([["category-item", _uM([["property", "all"], ["duration", "0.3s"], ["timingFunction", "cubic-bezier(0.4,0,0.2,1)"]])], ["category-name", _uM([["property", "all"], ["duration", "0.3s"]])], ["cart-btn", _uM([["property", "transform"], ["duration", "0.2s"]])], ["cart-expand-list", _uM([["property", "all"], ["duration", "0.3s"], ["timingFunction", "cubic-bezier(0.4,0,0.2,1)"]])], ["cart-footer", _uM([["property", "all"], ["duration", "0.3s"]])]])]])]
