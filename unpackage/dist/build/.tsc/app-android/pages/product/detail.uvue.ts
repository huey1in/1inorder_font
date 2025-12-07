
	import { BASE_URL, DEFAULT_FOOD_IMAGE } from '@/common/config.uts'
	
	const __sfc__ = defineComponent({
		data() {
			return {
				productId: 0,
				defaultFoodImage: DEFAULT_FOOD_IMAGE,
				product: {
					id: 0,
					name: '',
					description: '',
					detail: '',
					price: 0,
					original_price: 0,
					images: [] as Array<string>,
					sales_count: 0,
					stock_quantity: 0,
					category_name: ''
				},
				cartCount: 0
			}
		},
		onLoad(options: any) {
			if (options.id) {
				this.productId = parseInt(options.id)
				this.fetchProduct()
				this.fetchCartCount()
			}
		},
		methods: {
			fetchProduct() {
				uni.showLoading({ title: '加载中...' })
				uni.request({
					url: `${BASE_URL}/products/${this.productId}`,
					method: 'GET',
					success: (res: any) => {
						if (res.data.success) {
							const data = res.data.data
							if (data.product) {
								this.product = data.product
							} else if (data.products && data.products.length > 0) {
								// 兼容可能返回数组的情况
								this.product = data.products[0]
							} else {
								uni.showToast({ title: '商品不存在', icon: 'none' })
							}
						} else {
							uni.showToast({ title: '商品不存在', icon: 'none' })
						}
					},
					fail: () => {
						uni.showToast({ title: '网络错误', icon: 'none' })
					},
					complete: () => {
						uni.hideLoading()
					}
				})
			},
			
			fetchCartCount() {
				const token = uni.getStorageSync('token')
				if (!token) return
				
				uni.request({
					url: `${BASE_URL}/cart/count`,
					method: 'GET',
					header: { 'Authorization': `Bearer ${token}` },
					success: (res: any) => {
						if (res.data.success) {
							this.cartCount = res.data.data.count || 0
						}
					}
				})
			},
			
			addToCart() {
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
						product_id: this.productId,
						quantity: 1
					},
					success: (res: any) => {
						if (res.data.success) {
							uni.showToast({ title: '已加入购物车', icon: 'success' })
							this.fetchCartCount()
						} else {
							uni.showToast({ title: res.data.message || '添加失败', icon: 'none' })
						}
					}
				})
			},
			
			buyNow() {
				const token = uni.getStorageSync('token')
				if (!token) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					uni.navigateTo({ url: '/pages/login/login' })
					return
				}
				// 直接购买逻辑
				uni.navigateTo({ 
					url: `/pages/order/create?product_id=${this.productId}&quantity=1` 
				})
			},
			
			goToHome() {
				uni.switchTab({ url: '/pages/index/index' })
			},
			
			goToCart() {
				uni.switchTab({ url: '/pages/cart/cart' })
			}
		}
	})

export default __sfc__
function GenPagesProductDetailRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("swiper", _uM({
      class: "product-swiper",
      "indicator-dots": "",
      autoplay: "",
      circular: ""
    }), [
      _cE(Fragment, null, RenderHelpers.renderList(_ctx.product.images, (img, index, __index, _cached): any => {
        return _cE("swiper-item", _uM({ key: index }), [
          _cE("image", _uM({
            class: "swiper-image",
            src: img,
            mode: "aspectFill"
          }), null, 8 /* PROPS */, ["src"])
        ])
      }), 128 /* KEYED_FRAGMENT */),
      isTrue(!_ctx.product.images || _ctx.product.images.length === 0)
        ? _cE("swiper-item", _uM({ key: 0 }), [
            _cE("image", _uM({
              class: "swiper-image",
              src: _ctx.defaultFoodImage,
              mode: "aspectFill"
            }), null, 8 /* PROPS */, ["src"])
          ])
        : _cC("v-if", true)
    ]),
    _cE("view", _uM({ class: "product-info" }), [
      _cE("text", _uM({ class: "product-name" }), _tD(_ctx.product.name), 1 /* TEXT */),
      _cE("view", _uM({ class: "price-row" }), [
        _cE("text", _uM({ class: "price" }), "¥" + _tD(_ctx.product.price), 1 /* TEXT */),
        isTrue(_ctx.product.original_price)
          ? _cE("text", _uM({
              key: 0,
              class: "original-price"
            }), "¥" + _tD(_ctx.product.original_price), 1 /* TEXT */)
          : _cC("v-if", true),
        _cE("text", _uM({ class: "sales" }), "已售 " + _tD(_ctx.product.sales_count || 0), 1 /* TEXT */)
      ]),
      _cE("view", _uM({ class: "meta-row" }), [
        isTrue(_ctx.product.category_name)
          ? _cE("text", _uM({
              key: 0,
              class: "meta-text"
            }), "分类: " + _tD(_ctx.product.category_name), 1 /* TEXT */)
          : _cC("v-if", true),
        _cE("text", _uM({ class: "meta-text" }), "库存: " + _tD(_ctx.product.stock_quantity || 0), 1 /* TEXT */)
      ])
    ]),
    _cE("view", _uM({ class: "detail-section" }), [
      _cE("text", _uM({ class: "section-title" }), "商品详情"),
      _cE("view", _uM({ class: "detail-content" }), [
        _cE("text", _uM({ class: "detail-text" }), _tD(_ctx.product.detail || _ctx.product.description || '暂无详细介绍'), 1 /* TEXT */)
      ])
    ]),
    _cE("view", _uM({ class: "action-bar" }), [
      _cE("view", _uM({ class: "action-left" }), [
        _cE("view", _uM({
          class: "action-item",
          onClick: _ctx.goToHome
        }), [
          _cE("image", _uM({
            class: "action-icon",
            src: "/static/icons/icon-home.svg",
            mode: "aspectFit"
          })),
          _cE("text", _uM({ class: "action-label" }), "首页")
        ], 8 /* PROPS */, ["onClick"]),
        _cE("view", _uM({
          class: "action-item",
          onClick: _ctx.goToCart
        }), [
          _cE("image", _uM({
            class: "action-icon",
            src: "/static/icons/icon-cart.svg",
            mode: "aspectFit"
          })),
          _cE("text", _uM({ class: "action-label" }), "购物车"),
          _ctx.cartCount > 0
            ? _cE("view", _uM({
                key: 0,
                class: "cart-badge"
              }), [
                _cE("text", _uM({ class: "badge-num" }), _tD(_ctx.cartCount), 1 /* TEXT */)
              ])
            : _cC("v-if", true)
        ], 8 /* PROPS */, ["onClick"])
      ]),
      _cE("view", _uM({ class: "action-right" }), [
        _cE("view", _uM({
          class: "add-cart-btn",
          onClick: _ctx.addToCart
        }), [
          _cE("text", _uM({ class: "btn-text" }), "加入购物车")
        ], 8 /* PROPS */, ["onClick"]),
        _cE("view", _uM({
          class: "buy-now-btn",
          onClick: _ctx.buyNow
        }), [
          _cE("text", _uM({ class: "btn-text-primary" }), "立即购买 ♡")
        ], 8 /* PROPS */, ["onClick"])
      ])
    ])
  ])
}
const GenPagesProductDetailStyles = [_uM([["container", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["backgroundColor", "#FFF5F8"], ["paddingBottom", "160rpx"]]))], ["product-swiper", _pS(_uM([["width", "100%"], ["height", "650rpx"], ["borderTopLeftRadius", 0], ["borderTopRightRadius", 0], ["borderBottomRightRadius", "60rpx"], ["borderBottomLeftRadius", "60rpx"], ["overflow", "hidden"], ["boxShadow", "0 10rpx 30rpx rgba(255, 105, 180, 0.15)"]]))], ["swiper-image", _pS(_uM([["width", "100%"], ["height", "100%"]]))], ["product-info", _pS(_uM([["backgroundColor", "rgba(255,255,255,0.9)"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["marginTop", "30rpx"], ["marginRight", "24rpx"], ["marginBottom", "30rpx"], ["marginLeft", "24rpx"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"]]))], ["product-name", _pS(_uM([["fontSize", "40rpx"], ["color", "#5D4E6D"], ["lineHeight", 1.4]]))], ["price-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["marginTop", "24rpx"]]))], ["price", _pS(_uM([["fontSize", "52rpx"], ["color", "#FF69B4"], ["textShadow", "0 2rpx 4rpx rgba(255, 105, 180, 0.2)"]]))], ["original-price", _pS(_uM([["fontSize", "30rpx"], ["color", "#D4C4E3"], ["textDecoration", "line-through"], ["marginLeft", "20rpx"]]))], ["sales", _pS(_uM([["fontSize", "26rpx"], ["color", "#B8A9C9"], ["marginLeft", "auto"], ["backgroundColor", "#FFF0F5"], ["paddingTop", "6rpx"], ["paddingRight", "16rpx"], ["paddingBottom", "6rpx"], ["paddingLeft", "16rpx"], ["borderTopLeftRadius", "20rpx"], ["borderTopRightRadius", "20rpx"], ["borderBottomRightRadius", "20rpx"], ["borderBottomLeftRadius", "20rpx"]]))], ["meta-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["marginTop", "20rpx"], ["gap", "20rpx"]]))], ["meta-text", _pS(_uM([["fontSize", "24rpx"], ["color", "#888888"], ["backgroundColor", "#f5f5f5"], ["paddingTop", "4rpx"], ["paddingRight", "12rpx"], ["paddingBottom", "4rpx"], ["paddingLeft", "12rpx"], ["borderTopLeftRadius", "8rpx"], ["borderTopRightRadius", "8rpx"], ["borderBottomRightRadius", "8rpx"], ["borderBottomLeftRadius", "8rpx"]]))], ["product-desc", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"], ["marginTop", "24rpx"], ["lineHeight", 1.6], ["opacity", 0.9]]))], ["detail-section", _pS(_uM([["backgroundColor", "rgba(255,255,255,0.9)"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["marginTop", 0], ["marginRight", "24rpx"], ["marginBottom", "24rpx"], ["marginLeft", "24rpx"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"]]))], ["section-title", _pS(_uM([["fontSize", "34rpx"], ["color", "#5D4E6D"], ["marginBottom", "24rpx"], ["borderLeftWidth", "8rpx"], ["borderLeftStyle", "solid"], ["borderLeftColor", "#FF69B4"], ["paddingLeft", "20rpx"]]))], ["detail-content", _pS(_uM([["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0]]))], ["detail-text", _pS(_uM([["fontSize", "30rpx"], ["color", "#5D4E6D"], ["lineHeight", 1.8]]))], ["action-bar", _pS(_uM([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "24rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "30rpx"], ["marginTop", "24rpx"], ["marginRight", "24rpx"], ["marginBottom", "24rpx"], ["marginLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.95)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 -4rpx 30rpx rgba(255, 105, 180, 0.2)"], ["zIndex", 100], ["backdropFilter", "blur(10px)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.5)"], ["borderRightColor", "rgba(255,255,255,0.5)"], ["borderBottomColor", "rgba(255,255,255,0.5)"], ["borderLeftColor", "rgba(255,255,255,0.5)"]]))], ["action-left", _pS(_uM([["display", "flex"], ["flexDirection", "row"]]))], ["action-item", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["paddingTop", 0], ["paddingRight", "30rpx"], ["paddingBottom", 0], ["paddingLeft", "30rpx"], ["position", "relative"]]))], ["action-icon", _pS(_uM([["width", "52rpx"], ["height", "52rpx"]]))], ["action-label", _pS(_uM([["fontSize", "24rpx"], ["color", "#5D4E6D"], ["marginTop", "6rpx"]]))], ["cart-badge", _pS(_uM([["position", "absolute"], ["top", "-8rpx"], ["right", "16rpx"], ["minWidth", "36rpx"], ["height", "36rpx"], ["backgroundImage", "linear-gradient(135deg, #FF69B4 0%, #FF1493 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "18rpx"], ["borderTopRightRadius", "18rpx"], ["borderBottomRightRadius", "18rpx"], ["borderBottomLeftRadius", "18rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopWidth", "4rpx"], ["borderRightWidth", "4rpx"], ["borderBottomWidth", "4rpx"], ["borderLeftWidth", "4rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFFFFF"], ["borderRightColor", "#FFFFFF"], ["borderBottomColor", "#FFFFFF"], ["borderLeftColor", "#FFFFFF"], ["boxShadow", "0 2rpx 8rpx rgba(255, 20, 147, 0.3)"]]))], ["badge-num", _pS(_uM([["fontSize", "22rpx"], ["color", "#ffffff"], ["fontWeight", "bold"]]))], ["action-right", _pS(_uM([["flex", 1], ["display", "flex"], ["flexDirection", "row"], ["marginLeft", "24rpx"], ["gap", "20rpx"]]))], ["add-cart-btn", _pS(_uM([["flex", 1], ["paddingTop", "28rpx"], ["paddingRight", 0], ["paddingBottom", "28rpx"], ["paddingLeft", 0], ["backgroundColor", "#FFF0F5"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopWidth", 2], ["borderRightWidth", 2], ["borderBottomWidth", 2], ["borderLeftWidth", 2], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["transitionProperty", "all"], ["transitionDuration", "0.2s"], ["backgroundColor:active", "#FFE4EC"]]))], ["buy-now-btn", _pS(_uM([["flex", 1], ["paddingTop", "28rpx"], ["paddingRight", 0], ["paddingBottom", "28rpx"], ["paddingLeft", 0], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["boxShadow", "0 6rpx 20rpx rgba(255, 105, 180, 0.4)"], ["transitionProperty", "transform"], ["transitionDuration", "0.2s"], ["transform:active", "scale(0.95)"]]))], ["btn-text", _pS(_uM([["fontSize", "30rpx"], ["color", "#FF69B4"]]))], ["btn-text-primary", _pS(_uM([["fontSize", "30rpx"], ["color", "#ffffff"], ["letterSpacing", 1]]))], ["@TRANSITION", _uM([["add-cart-btn", _uM([["property", "all"], ["duration", "0.2s"]])], ["buy-now-btn", _uM([["property", "transform"], ["duration", "0.2s"]])]])]])]
