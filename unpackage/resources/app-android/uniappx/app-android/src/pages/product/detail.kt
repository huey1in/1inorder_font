@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uni.UNI9239644
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uniapp.framework.*
import io.dcloud.uniapp.runtime.*
import io.dcloud.uniapp.vue.*
import io.dcloud.uniapp.vue.shared.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesProductDetail : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            if (options.id) {
                this.productId = parseInt(options.id)
                this.fetchProduct()
                this.fetchCartCount()
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("swiper", _uM("class" to "product-swiper", "indicator-dots" to "", "autoplay" to "", "circular" to ""), _uA(
                _cE(Fragment, null, RenderHelpers.renderList(_ctx.product.images, fun(img, index, __index, _cached): Any {
                    return _cE("swiper-item", _uM("key" to index), _uA(
                        _cE("image", _uM("class" to "swiper-image", "src" to img, "mode" to "aspectFill"), null, 8, _uA(
                            "src"
                        ))
                    ))
                }
                ), 128),
                if (isTrue(!_ctx.product.images || _ctx.product.images.length === 0)) {
                    _cE("swiper-item", _uM("key" to 0), _uA(
                        _cE("image", _uM("class" to "swiper-image", "src" to _ctx.defaultFoodImage, "mode" to "aspectFill"), null, 8, _uA(
                            "src"
                        ))
                    ))
                } else {
                    _cC("v-if", true)
                }
            )),
            _cE("view", _uM("class" to "product-info"), _uA(
                _cE("text", _uM("class" to "product-name"), _tD(_ctx.product.name), 1),
                _cE("view", _uM("class" to "price-row"), _uA(
                    _cE("text", _uM("class" to "price"), "¥" + _tD(_ctx.product.price), 1),
                    if (isTrue(_ctx.product.original_price)) {
                        _cE("text", _uM("key" to 0, "class" to "original-price"), "¥" + _tD(_ctx.product.original_price), 1)
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cE("text", _uM("class" to "sales"), "已售 " + _tD(_ctx.product.sales || 0), 1)
                )),
                _cE("text", _uM("class" to "product-desc"), _tD(_ctx.product.description), 1)
            )),
            _cE("view", _uM("class" to "detail-section"), _uA(
                _cE("text", _uM("class" to "section-title"), "商品详情"),
                _cE("view", _uM("class" to "detail-content"), _uA(
                    _cE("text", _uM("class" to "detail-text"), _tD(_ctx.product.detail || "暂无详细介绍"), 1)
                ))
            )),
            _cE("view", _uM("class" to "action-bar"), _uA(
                _cE("view", _uM("class" to "action-left"), _uA(
                    _cE("view", _uM("class" to "action-item", "onClick" to _ctx.goToHome), _uA(
                        _cE("image", _uM("class" to "action-icon", "src" to "/static/icons/icon-home.svg", "mode" to "aspectFit")),
                        _cE("text", _uM("class" to "action-label"), "首页")
                    ), 8, _uA(
                        "onClick"
                    )),
                    _cE("view", _uM("class" to "action-item", "onClick" to _ctx.goToCart), _uA(
                        _cE("image", _uM("class" to "action-icon", "src" to "/static/icons/icon-cart.svg", "mode" to "aspectFit")),
                        _cE("text", _uM("class" to "action-label"), "购物车"),
                        if (_ctx.cartCount > 0) {
                            _cE("view", _uM("key" to 0, "class" to "cart-badge"), _uA(
                                _cE("text", _uM("class" to "badge-num"), _tD(_ctx.cartCount), 1)
                            ))
                        } else {
                            _cC("v-if", true)
                        }
                    ), 8, _uA(
                        "onClick"
                    ))
                )),
                _cE("view", _uM("class" to "action-right"), _uA(
                    _cE("view", _uM("class" to "add-cart-btn", "onClick" to _ctx.addToCart), _uA(
                        _cE("text", _uM("class" to "btn-text"), "加入购物车")
                    ), 8, _uA(
                        "onClick"
                    )),
                    _cE("view", _uM("class" to "buy-now-btn", "onClick" to _ctx.buyNow), _uA(
                        _cE("text", _uM("class" to "btn-text-primary"), "立即购买 ♡")
                    ), 8, _uA(
                        "onClick"
                    ))
                ))
            ))
        ))
    }
    open var productId: Number by `$data`
    open var defaultFoodImage: Any? by `$data`
    open var product: UTSJSONObject by `$data`
    open var cartCount: Number by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("productId" to 0, "defaultFoodImage" to DEFAULT_FOOD_IMAGE, "product" to object : UTSJSONObject() {
            var id: Number = 0
            var name = ""
            var description = ""
            var detail = ""
            var price: Number = 0
            var original_price: Number = 0
            var images = _uA<String>()
            var sales: Number = 0
        }, "cartCount" to 0)
    }
    open var fetchProduct = ::gen_fetchProduct_fn
    open fun gen_fetchProduct_fn() {
        uni_showLoading(ShowLoadingOptions(title = "加载中..."))
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/products/" + this.productId, method = "GET", success = fun(res: Any){
            if (res.data.success && res.data.data.product) {
                this.product = res.data.data.product
            } else {
                uni_showToast(ShowToastOptions(title = "商品不存在", icon = "none"))
            }
        }
        , fail = fun(_){
            uni_showToast(ShowToastOptions(title = "网络错误", icon = "none"))
        }
        , complete = fun(_){
            uni_hideLoading()
        }
        ))
    }
    open var fetchCartCount = ::gen_fetchCartCount_fn
    open fun gen_fetchCartCount_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/count", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success) {
                this.cartCount = res.data.data.count || 0
            }
        }
        ))
    }
    open var addToCart = ::gen_addToCart_fn
    open fun gen_addToCart_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/add", method = "POST", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = _uO("product_id" to this.productId, "quantity" to 1), success = fun(res: Any){
            if (res.data.success) {
                uni_showToast(ShowToastOptions(title = "已加入购物车", icon = "success"))
                this.fetchCartCount()
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "添加失败", icon = "none"))
            }
        }
        ))
    }
    open var buyNow = ::gen_buyNow_fn
    open fun gen_buyNow_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/order/create?product_id=" + this.productId + "&quantity=1"))
    }
    open var goToHome = ::gen_goToHome_fn
    open fun gen_goToHome_fn() {
        uni_switchTab(SwitchTabOptions(url = "/pages/index/index"))
    }
    open var goToCart = ::gen_goToCart_fn
    open fun gen_goToCart_fn() {
        uni_switchTab(SwitchTabOptions(url = "/pages/cart/cart"))
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ), _uA(
                GenApp.styles
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#FFF5F8", "paddingBottom" to "160rpx")), "product-swiper" to _pS(_uM("width" to "100%", "height" to "650rpx", "borderTopLeftRadius" to 0, "borderTopRightRadius" to 0, "borderBottomRightRadius" to "60rpx", "borderBottomLeftRadius" to "60rpx", "overflow" to "hidden", "boxShadow" to "0 10rpx 30rpx rgba(255, 105, 180, 0.15)")), "swiper-image" to _pS(_uM("width" to "100%", "height" to "100%")), "product-info" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "marginTop" to "30rpx", "marginRight" to "24rpx", "marginBottom" to "30rpx", "marginLeft" to "24rpx", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "product-name" to _pS(_uM("fontSize" to "40rpx", "color" to "#5D4E6D", "lineHeight" to 1.4)), "price-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "marginTop" to "24rpx")), "price" to _pS(_uM("fontSize" to "52rpx", "color" to "#FF69B4", "textShadow" to "0 2rpx 4rpx rgba(255, 105, 180, 0.2)")), "original-price" to _pS(_uM("fontSize" to "30rpx", "color" to "#D4C4E3", "textDecoration" to "line-through", "marginLeft" to "20rpx")), "sales" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9", "marginLeft" to "auto", "backgroundColor" to "#FFF0F5", "paddingTop" to "6rpx", "paddingRight" to "16rpx", "paddingBottom" to "6rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx")), "product-desc" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D", "marginTop" to "24rpx", "lineHeight" to 1.6, "opacity" to 0.9)), "detail-section" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "marginTop" to 0, "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "section-title" to _pS(_uM("fontSize" to "34rpx", "color" to "#5D4E6D", "marginBottom" to "24rpx", "borderLeftWidth" to "8rpx", "borderLeftStyle" to "solid", "borderLeftColor" to "#FF69B4", "paddingLeft" to "20rpx")), "detail-content" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "detail-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D", "lineHeight" to 1.8)), "action-bar" to _pS(_uM("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "24rpx", "paddingRight" to "30rpx", "paddingBottom" to "24rpx", "paddingLeft" to "30rpx", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.95)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 -4rpx 30rpx rgba(255, 105, 180, 0.2)", "zIndex" to 100, "backdropFilter" to "blur(10px)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.5)", "borderRightColor" to "rgba(255,255,255,0.5)", "borderBottomColor" to "rgba(255,255,255,0.5)", "borderLeftColor" to "rgba(255,255,255,0.5)")), "action-left" to _pS(_uM("display" to "flex", "flexDirection" to "row")), "action-item" to _pS(_uM("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "30rpx", "paddingBottom" to 0, "paddingLeft" to "30rpx", "position" to "relative")), "action-icon" to _pS(_uM("width" to "52rpx", "height" to "52rpx")), "action-label" to _pS(_uM("fontSize" to "24rpx", "color" to "#5D4E6D", "marginTop" to "6rpx")), "cart-badge" to _pS(_uM("position" to "absolute", "top" to "-8rpx", "right" to "16rpx", "minWidth" to "36rpx", "height" to "36rpx", "backgroundImage" to "linear-gradient(135deg, #FF69B4 0%, #FF1493 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "18rpx", "borderTopRightRadius" to "18rpx", "borderBottomRightRadius" to "18rpx", "borderBottomLeftRadius" to "18rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFFFFF", "borderRightColor" to "#FFFFFF", "borderBottomColor" to "#FFFFFF", "borderLeftColor" to "#FFFFFF", "boxShadow" to "0 2rpx 8rpx rgba(255, 20, 147, 0.3)")), "badge-num" to _pS(_uM("fontSize" to "22rpx", "color" to "#ffffff", "fontWeight" to "bold")), "action-right" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "row", "marginLeft" to "24rpx", "gap" to "20rpx")), "add-cart-btn" to _pS(_uM("flex" to 1, "paddingTop" to "28rpx", "paddingRight" to 0, "paddingBottom" to "28rpx", "paddingLeft" to 0, "backgroundColor" to "#FFF0F5", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "transitionProperty" to "all", "transitionDuration" to "0.2s", "backgroundColor:active" to "#FFE4EC")), "buy-now-btn" to _pS(_uM("flex" to 1, "paddingTop" to "28rpx", "paddingRight" to 0, "paddingBottom" to "28rpx", "paddingLeft" to 0, "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.4)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.95)")), "btn-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#FF69B4")), "btn-text-primary" to _pS(_uM("fontSize" to "30rpx", "color" to "#ffffff", "letterSpacing" to 1)), "@TRANSITION" to _uM("add-cart-btn" to _uM("property" to "all", "duration" to "0.2s"), "buy-now-btn" to _uM("property" to "transform", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
