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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesCartCart : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onPageShow(fun() {
            console.log("购物车页面 onShow 触发")
            this.fetchCart()
        }
        , __ins)
        onLoad(fun(_: OnLoadOptions) {
            console.log("购物车页面 onLoad 触发")
            this.fetchCart()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            if (_ctx.cartItems.length === 0) {
                _cE("view", _uM("key" to 0, "class" to "empty-cart"), _uA(
                    _cE("image", _uM("class" to "empty-icon", "src" to "/static/icons/icon-cart.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "empty-text"), "购物车空空的~"),
                    _cE("view", _uM("class" to "go-shopping", "onClick" to _ctx.goShopping), _uA(
                        _cE("text", _uM("class" to "go-shopping-text"), "去逛逛 ♡")
                    ), 8, _uA(
                        "onClick"
                    ))
                ))
            } else {
                _cC("v-if", true)
            }
            ,
            if (_ctx.cartItems.length > 0) {
                _cE("scroll-view", _uM("key" to 1, "class" to "cart-list", "scroll-y" to ""), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.cartItems, fun(item, __key, __index, _cached): Any {
                        return _cE("view", _uM("class" to "cart-item", "key" to item.id), _uA(
                            _cE("view", _uM("class" to "checkbox", "onClick" to fun(){
                                _ctx.toggleSelect(item)
                            }), _uA(
                                _cE("text", _uM("class" to _nC(_uA(
                                    "check-icon",
                                    if (item.selected) {
                                        "checked"
                                    } else {
                                        ""
                                    }
                                ))), _tD(if (item.selected) {
                                    "♡"
                                } else {
                                    ""
                                }), 3)
                            ), 8, _uA(
                                "onClick"
                            )),
                            _cE("image", _uM("class" to "item-image", "src" to _ctx.getProductImage(item), "mode" to "aspectFill"), null, 8, _uA(
                                "src"
                            )),
                            _cE("view", _uM("class" to "item-info"), _uA(
                                _cE("text", _uM("class" to "item-name"), _tD(item.name || "商品"), 1),
                                _cE("text", _uM("class" to "item-price"), "¥" + _tD(item.price || 0), 1),
                                _cE("view", _uM("class" to "quantity-box"), _uA(
                                    _cE("view", _uM("class" to "qty-btn", "onClick" to fun(){
                                        _ctx.changeQuantity(item, -1)
                                    }), _uA(
                                        _cE("text", _uM("class" to "qty-btn-text"), "-")
                                    ), 8, _uA(
                                        "onClick"
                                    )),
                                    _cE("text", _uM("class" to "qty-num"), _tD(item.quantity), 1),
                                    _cE("view", _uM("class" to "qty-btn", "onClick" to fun(){
                                        _ctx.changeQuantity(item, 1)
                                    }), _uA(
                                        _cE("text", _uM("class" to "qty-btn-text"), "+")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                ))
                            )),
                            _cE("view", _uM("class" to "delete-btn", "onClick" to fun(){
                                _ctx.removeItem(item)
                            }), _uA(
                                _cE("image", _uM("class" to "delete-icon", "src" to "/static/icons/icon-delete.svg", "mode" to "aspectFit"))
                            ), 8, _uA(
                                "onClick"
                            ))
                        ))
                    }), 128)
                ))
            } else {
                _cC("v-if", true)
            }
            ,
            if (_ctx.cartItems.length > 0) {
                _cE("view", _uM("key" to 2, "class" to "checkout-bar"), _uA(
                    _cE("view", _uM("class" to "select-all", "onClick" to _ctx.toggleSelectAll), _uA(
                        _cE("view", _uM("class" to _nC(_uA(
                            "checkbox-icon",
                            if (_ctx.allSelected) {
                                "checked"
                            } else {
                                ""
                            }
                        ))), _uA(
                            if (isTrue(_ctx.allSelected)) {
                                _cE("text", _uM("key" to 0), "♡")
                            } else {
                                _cC("v-if", true)
                            }
                        ), 2),
                        _cE("text", _uM("class" to "select-all-text"), "全选")
                    ), 8, _uA(
                        "onClick"
                    )),
                    _cE("view", _uM("class" to "total-info"), _uA(
                        _cE("text", _uM("class" to "total-label"), "合计:"),
                        _cE("text", _uM("class" to "total-amount"), "¥" + _tD(_ctx.totalAmount.toFixed(2)), 1)
                    )),
                    _cE("view", _uM("class" to "checkout-btn", "onClick" to _ctx.checkout), _uA(
                        _cE("text", _uM("class" to "checkout-text"), "去结算(" + _tD(_ctx.selectedCount) + ") ♡", 1)
                    ), 8, _uA(
                        "onClick"
                    ))
                ))
            } else {
                _cC("v-if", true)
            }
        ))
    }
    open var cartItems: UTSArray<CartItem> by `$data`
    open var allSelected: Boolean by `$data`
    open var selectedCount: Number by `$data`
    open var totalAmount: Number by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("cartItems" to _uA<CartItem>(), "allSelected" to computed<Boolean>(fun(): Boolean {
            return this.cartItems.length > 0 && this.cartItems.every(fun(item: CartItem): Boolean {
                return item.selected
            }
            )
        }
        ), "selectedCount" to computed<Number>(fun(): Number {
            return this.cartItems.filter(fun(item: CartItem): Boolean {
                return item.selected
            }
            ).reduce(fun(sum: Number, item: CartItem): Number {
                return sum + item.quantity
            }
            , 0)
        }
        ), "totalAmount" to computed<Number>(fun(): Number {
            return this.cartItems.filter(fun(item: CartItem): Boolean {
                return item.selected
            }
            ).reduce(fun(sum: Number, item: CartItem): Number {
                return sum + (item.price || 0) * item.quantity
            }
            , 0)
        }
        ))
    }
    open var getProductImage = ::gen_getProductImage_fn
    open fun gen_getProductImage_fn(item: CartItem): String {
        if (item.images && item.images.length > 0) {
            return item.images[0]
        }
        return DEFAULT_FOOD_IMAGE
    }
    open var fetchCart = ::gen_fetchCart_fn
    open fun gen_fetchCart_fn() {
        console.log("fetchCart 开始执行")
        val token = uni_getStorageSync("token")
        console.log("token:", if (token) {
            "已获取"
        } else {
            "未获取"
        }
        )
        if (!token) {
            console.log("未登录，跳转到登录页")
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            setTimeout(fun(){
                uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            }
            , 1500)
            return
        }
        uni_showLoading(ShowLoadingOptions(title = "加载中..."))
        console.log("开始请求购物车接口")
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            console.log("购物车接口响应:", JSON.stringify(res.data))
            if (res.data.success && res.data.data.cart) {
                val items = res.data.data.cart.items || _uA()
                this.cartItems = items.map(fun(item: Any): UTSJSONObject {
                    return (object : UTSJSONObject() {
                        var id = item.cart_item_id
                        var product_id = item.product_id
                        var name = item.name
                        var price = item.price
                        var quantity = item.quantity
                        var images = item.images || _uA()
                        var selected = true
                    })
                }
                )
                console.log("处理后的购物车数据:", JSON.stringify(this.cartItems))
            }
        }
        , fail = fun(_){
            uni_showToast(ShowToastOptions(title = "加载失败", icon = "none"))
        }
        , complete = fun(_){
            uni_hideLoading()
        }
        ))
    }
    open var toggleSelect = ::gen_toggleSelect_fn
    open fun gen_toggleSelect_fn(item: CartItem) {
        item.selected = !item.selected
    }
    open var toggleSelectAll = ::gen_toggleSelectAll_fn
    open fun gen_toggleSelectAll_fn() {
        val newStatus = !this.allSelected
        this.cartItems.forEach(fun(item: CartItem){
            item.selected = newStatus
        }
        )
    }
    open var changeQuantity = ::gen_changeQuantity_fn
    open fun gen_changeQuantity_fn(item: CartItem, delta: Number) {
        val newQty = item.quantity + delta
        if (newQty < 1) {
            return
        }
        val token = uni_getStorageSync("token")
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/item/" + item.id, method = "PATCH", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = object : UTSJSONObject() {
            var quantity = newQty
        }, success = fun(res: Any){
            if (res.data.success) {
                item.quantity = newQty
            }
        }
        ))
    }
    open var removeItem = ::gen_removeItem_fn
    open fun gen_removeItem_fn(item: CartItem) {
        uni_showModal(ShowModalOptions(title = "确认删除", content = "确定要删除该商品吗？", success = fun(res){
            if (res.confirm) {
                val token = uni_getStorageSync("token")
                uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/item/" + item.id, method = "DELETE", header = object : UTSJSONObject() {
                    var Authorization = "Bearer " + token
                    var `Content-Type` = "application/json"
                }, success = fun(res: Any){
                    if (res.data.success) {
                        val index = this.cartItems.indexOf(item)
                        if (index > -1) {
                            this.cartItems.splice(index, 1)
                        }
                    }
                }
                ))
            }
        }
        ))
    }
    open var checkout = ::gen_checkout_fn
    open fun gen_checkout_fn() {
        val selectedItems = this.cartItems.filter(fun(item: CartItem): Boolean {
            return item.selected
        }
        )
        if (selectedItems.length === 0) {
            uni_showToast(ShowToastOptions(title = "请选择商品", icon = "none"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/order/create"))
    }
    open var goShopping = ::gen_goShopping_fn
    open fun gen_goShopping_fn() {
        uni_navigateBack(null)
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
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#FFF5F8")), "empty-cart" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "column", "justifyContent" to "center", "alignItems" to "center", "paddingTop" to "100rpx", "paddingRight" to "100rpx", "paddingBottom" to "100rpx", "paddingLeft" to "100rpx")), "empty-icon" to _pS(_uM("width" to "240rpx", "height" to "240rpx", "opacity" to 0.9, "filter" to "drop-shadow(0 10rpx 20rpx rgba(255, 105, 180, 0.2))", "animation" to "float 3s ease-in-out infinite")), "empty-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#B8A9C9", "marginTop" to "40rpx")), "go-shopping" to _pS(_uM("marginTop" to "60rpx", "paddingTop" to "28rpx", "paddingRight" to "80rpx", "paddingBottom" to "28rpx", "paddingLeft" to "80rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.3)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.95)")), "go-shopping-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#ffffff", "letterSpacing" to 1)), "cart-list" to _pS(_uM("flex" to 1, "paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "180rpx", "paddingLeft" to "24rpx")), "cart-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "backgroundColor" to "rgba(255,255,255,0.9)", "marginBottom" to "24rpx", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.6)", "borderRightColor" to "rgba(255,255,255,0.6)", "borderBottomColor" to "rgba(255,255,255,0.6)", "borderLeftColor" to "rgba(255,255,255,0.6)")), "checkbox" to _pS(_uM("width" to "48rpx", "height" to "48rpx", "borderTopWidth" to "3rpx", "borderRightWidth" to "3rpx", "borderBottomWidth" to "3rpx", "borderLeftWidth" to "3rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "marginRight" to "24rpx", "backgroundColor" to "#FFF5F8", "transitionProperty" to "all", "transitionDuration" to "0.2s")), "check-icon" to _uM("" to _uM("fontSize" to "28rpx", "color" to "rgba(0,0,0,0)", "width" to "100%", "height" to "100%", "display" to "flex", "justifyContent" to "center", "alignItems" to "center"), ".checked" to _uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#ffffff", "boxShadow" to "0 2rpx 8rpx rgba(255, 105, 180, 0.3)")), "item-image" to _pS(_uM("width" to "180rpx", "height" to "180rpx", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5", "boxShadow" to "0 4rpx 12rpx rgba(0,0,0,0.05)")), "item-info" to _pS(_uM("flex" to 1, "marginLeft" to "24rpx", "display" to "flex", "flexDirection" to "column", "height" to "180rpx", "justifyContent" to "space-between")), "item-name" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D", "fontWeight" to "700", "lineHeight" to 1.4)), "item-price" to _pS(_uM("fontSize" to "36rpx", "color" to "#FF69B4")), "quantity-box" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#FFF0F5", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "paddingTop" to "4rpx", "paddingRight" to "4rpx", "paddingBottom" to "4rpx", "paddingLeft" to "4rpx", "alignSelf" to "flex-start")), "qty-btn" to _pS(_uM("width" to "56rpx", "height" to "56rpx", "backgroundColor" to "#FFFFFF", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 2rpx 6rpx rgba(0,0,0,0.05)", "backgroundColor:active" to "#FFE4EC")), "qty-btn-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FF69B4", "fontWeight" to "bold")), "qty-num" to _pS(_uM("fontSize" to "28rpx", "color" to "#5D4E6D", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx", "fontWeight" to "bold", "minWidth" to "40rpx", "textAlign" to "center")), "delete-btn" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx", "opacity" to 0.6, "transitionProperty" to "opacity", "transitionDuration" to "0.2s", "opacity:active" to 1)), "delete-icon" to _pS(_uM("width" to "44rpx", "height" to "44rpx")), "checkout-bar" to _pS(_uM("position" to "fixed", "bottom" to CSS_VAR_WINDOW_BOTTOM, "left" to 0, "right" to 0, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "24rpx", "paddingRight" to "30rpx", "paddingBottom" to "24rpx", "paddingLeft" to "30rpx", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.95)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 -4rpx 30rpx rgba(255, 105, 180, 0.2)", "zIndex" to 100, "backdropFilter" to "blur(10px)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.5)", "borderRightColor" to "rgba(255,255,255,0.5)", "borderBottomColor" to "rgba(255,255,255,0.5)", "borderLeftColor" to "rgba(255,255,255,0.5)")), "select-all" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx")), "checkbox-icon" to _uM("" to _uM("width" to "44rpx", "height" to "44rpx", "borderTopWidth" to "3rpx", "borderRightWidth" to "3rpx", "borderBottomWidth" to "3rpx", "borderLeftWidth" to "3rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "fontSize" to "24rpx", "color" to "#FFF5F8", "backgroundColor" to "#FFF5F8", "transitionProperty" to "all", "transitionDuration" to "0.2s"), ".checked" to _uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopColor" to "#FF69B4", "borderRightColor" to "#FF69B4", "borderBottomColor" to "#FF69B4", "borderLeftColor" to "#FF69B4", "color" to "#ffffff", "boxShadow" to "0 2rpx 8rpx rgba(255, 105, 180, 0.3)")), "select-all-text" to _pS(_uM("fontSize" to "28rpx", "color" to "#5D4E6D", "marginLeft" to "16rpx")), "total-info" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "row", "justifyContent" to "flex-end", "paddingRight" to "24rpx")), "total-label" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "total-amount" to _pS(_uM("fontSize" to "40rpx", "color" to "#FF69B4", "marginLeft" to "10rpx")), "checkout-btn" to _pS(_uM("paddingTop" to "24rpx", "paddingRight" to "48rpx", "paddingBottom" to "24rpx", "paddingLeft" to "48rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.4)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.95)")), "checkout-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#ffffff", "letterSpacing" to 1)), "@FONT-FACE" to _uM("0" to _uM()), "@TRANSITION" to _uM("go-shopping" to _uM("property" to "transform", "duration" to "0.2s"), "checkbox" to _uM("property" to "all", "duration" to "0.2s"), "delete-btn" to _uM("property" to "opacity", "duration" to "0.2s"), "checkbox-icon" to _uM("property" to "all", "duration" to "0.2s"), "checkout-btn" to _uM("property" to "transform", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
