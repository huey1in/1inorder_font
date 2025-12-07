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
import io.dcloud.uniapp.extapi.redirectTo as uni_redirectTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesOrderCreate : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.fetchCart()
            this.fetchDefaultAddress()
            this.fetchShopInfo()
            this.fillContactFromStorage()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "用餐方式"),
                _cE("view", _uM("class" to "type-options"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.typeOptions, fun(item, __key, __index, _cached): Any {
                        return _cE("view", _uM("class" to _nC(_uA(
                            "type-chip",
                            _uM("active" to (_ctx.orderType === item.value))
                        )), "key" to item.value, "onClick" to fun(){
                            _ctx.changeOrderType(item.value)
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "type-text"), _tD(item.label), 1)
                        ), 10, _uA(
                            "onClick"
                        ))
                    }
                    ), 128)
                ))
            )),
            _cE("view", _uM("class" to "section address-section", "onClick" to _ctx.selectAddress), _uA(
                _cE("view", _uM("class" to "section-header-row"), _uA(
                    _cE("text", _uM("class" to "section-title"), "配送信息"),
                    _cE("text", _uM("class" to "arrow"), "➜")
                )),
                if (_ctx.orderType === "delivery") {
                    _cE(Fragment, _uM("key" to 0), _uA(
                        if (isTrue(_ctx.orderInfo.address)) {
                            _cE("view", _uM("key" to 0, "class" to "address-display"), _uA(
                                _cE("view", _uM("class" to "contact-row"), _uA(
                                    _cE("text", _uM("class" to "contact-name"), _tD(_ctx.orderInfo.name), 1),
                                    _cE("text", _uM("class" to "contact-phone"), _tD(_ctx.orderInfo.phone), 1)
                                )),
                                _cE("text", _uM("class" to "address-text"), _tD(_ctx.orderInfo.address), 1)
                            ))
                        } else {
                            _cE("view", _uM("key" to 1, "class" to "address-placeholder"), _uA(
                                _cE("view", _uM("class" to "placeholder-icon-box"), _uA(
                                    _cE("image", _uM("class" to "placeholder-icon", "src" to "/static/icons/icon-address.svg", "mode" to "aspectFit"))
                                )),
                                _cE("text", _uM("class" to "placeholder-text"), "请选择收货地址")
                            ))
                        }
                    ), 64)
                } else {
                    _cE(Fragment, _uM("key" to 1), _uA(
                        if (isTrue(_ctx.orderInfo.name || _ctx.orderInfo.phone)) {
                            _cE("view", _uM("key" to 0, "class" to "address-display"), _uA(
                                _cE("view", _uM("class" to "contact-row"), _uA(
                                    _cE("text", _uM("class" to "contact-name"), _tD(_ctx.orderInfo.name), 1),
                                    _cE("text", _uM("class" to "contact-phone"), _tD(_ctx.orderInfo.phone), 1)
                                ))
                            ))
                        } else {
                            _cE("view", _uM("key" to 1, "class" to "address-placeholder"), _uA(
                                _cE("view", _uM("class" to "placeholder-icon-box"), _uA(
                                    _cE("image", _uM("class" to "placeholder-icon", "src" to "/static/icons/icon-address.svg", "mode" to "aspectFit"))
                                )),
                                _cE("text", _uM("class" to "placeholder-text"), "自取：请选择地址以填充联系人信息")
                            ))
                        }
                    ), 64)
                }
            ), 8, _uA(
                "onClick"
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "商品清单"),
                _cE("view", _uM("class" to "order-items"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.cartItems, fun(item, __key, __index, _cached): Any {
                        return _cE("view", _uM("class" to "order-item", "key" to item.id), _uA(
                            _cE("image", _uM("class" to "item-image", "src" to _ctx.getProductImage(item), "mode" to "aspectFill"), null, 8, _uA(
                                "src"
                            )),
                            _cE("view", _uM("class" to "item-info"), _uA(
                                _cE("text", _uM("class" to "item-name"), _tD(item.name || "商品"), 1),
                                _cE("view", _uM("class" to "item-bottom"), _uA(
                                    _cE("text", _uM("class" to "item-price"), "￥" + _tD(item.price || 0), 1),
                                    _cE("text", _uM("class" to "item-qty"), "x" + _tD(item.quantity), 1)
                                ))
                            ))
                        ))
                    }
                    ), 128)
                ))
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "订单备注"),
                _cE("textarea", _uM("class" to "remark-input", "modelValue" to _ctx.orderInfo.remark, "onInput" to fun(`$event`: UniInputEvent){
                    _ctx.orderInfo.remark = `$event`.detail.value
                }
                , "placeholder" to "如需备注请输入（如：少辣、不要香菜等）"), null, 40, _uA(
                    "modelValue",
                    "onInput"
                ))
            )),
            _cE("view", _uM("class" to "section amount-section"), _uA(
                _cE("view", _uM("class" to "amount-row"), _uA(
                    _cE("text", _uM("class" to "amount-label"), "商品金额"),
                    _cE("text", _uM("class" to "amount-value"), "￥" + _tD(_ctx.subtotal.toFixed(2)), 1)
                )),
                if (_ctx.orderType === "delivery") {
                    _cE("view", _uM("key" to 0, "class" to "amount-row"), _uA(
                        _cE("text", _uM("class" to "amount-label"), "配送费"),
                        _cE("text", _uM("class" to "amount-value"), "￥" + _tD(_ctx.deliveryFee.toFixed(2)), 1)
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(_ctx.orderType === "delivery" && _ctx.shopInfo.min_order_amount > 0)) {
                    _cE("view", _uM("key" to 1, "class" to "amount-row hint-row"), _uA(
                        _cE("text", _uM("class" to "hint-text"), "起送金额 ¥" + _tD(_ctx.shopInfo.min_order_amount), 1),
                        _cE("text", _uM("class" to _nC(_uA(
                            "hint-status",
                            if (_ctx.subtotal >= _ctx.shopInfo.min_order_amount) {
                                "success"
                            } else {
                                "warning"
                            }
                        ))), _tD(if (_ctx.subtotal >= _ctx.shopInfo.min_order_amount) {
                            "已满足"
                        } else {
                            "未满足"
                        }), 3)
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                _cE("view", _uM("class" to "amount-row total-row"), _uA(
                    _cE("text", _uM("class" to "amount-label"), "合计"),
                    _cE("text", _uM("class" to "total-value"), "￥" + _tD(_ctx.totalAmount.toFixed(2)), 1)
                ))
            )),
            _cE("view", _uM("class" to "submit-bar"), _uA(
                _cE("view", _uM("class" to "total-box"), _uA(
                    _cE("text", _uM("class" to "total-label"), "待支付："),
                    _cE("text", _uM("class" to "total-amount"), "￥" + _tD(_ctx.totalAmount.toFixed(2)), 1)
                )),
                _cE("view", _uM("class" to "submit-btn", "onClick" to _ctx.submitOrder), _uA(
                    _cE("text", _uM("class" to "submit-text"), "提交订单 ✔")
                ), 8, _uA(
                    "onClick"
                ))
            ))
        ))
    }
    open var cartItems: UTSArray<CartItem1> by `$data`
    open var orderInfo: UTSJSONObject by `$data`
    open var orderType: String by `$data`
    open var typeOptions: UTSArray<UTSJSONObject> by `$data`
    open var shopInfo: UTSJSONObject by `$data`
    open var subtotal: Number by `$data`
    open var deliveryFee: Number by `$data`
    open var totalAmount: Number by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("cartItems" to _uA<CartItem1>(), "orderInfo" to object : UTSJSONObject() {
            var name = ""
            var phone = ""
            var address = ""
            var remark = ""
            var tableNumber = ""
        }, "orderType" to "delivery", "typeOptions" to _uA(
            object : UTSJSONObject() {
                var value = "delivery"
                var label = "外卖"
            },
            object : UTSJSONObject() {
                var value = "pickup"
                var label = "自取"
            }
        ), "shopInfo" to object : UTSJSONObject() {
            var delivery_fee: Number = 0
            var min_order_amount: Number = 0
        }, "subtotal" to computed<Number>(fun(): Number {
            return this.cartItems.reduce(fun(sum: Number, item: CartItem1): Number {
                return sum + (item.price || 0) * item.quantity
            }
            , 0)
        }
        ), "deliveryFee" to computed<Number>(fun(): Number {
            return if (this.orderType === "delivery") {
                (this.shopInfo.delivery_fee || 0)
            } else {
                0
            }
        }
        ), "totalAmount" to computed<Number>(fun(): Number {
            return this.subtotal + this.deliveryFee
        }
        ))
    }
    open var fetchShopInfo = ::gen_fetchShopInfo_fn
    open fun gen_fetchShopInfo_fn() {
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/shop/info", method = "GET", success = fun(res: Any){
            if (res.data.success && res.data.data) {
                this.shopInfo = res.data.data
            }
        }
        ))
    }
    open var fillContactFromStorage = ::gen_fillContactFromStorage_fn
    open fun gen_fillContactFromStorage_fn() {
        val user = uni_getStorageSync("userInfo")
        if (user) {
            if (!this.orderInfo.name) {
                this.orderInfo.name = user.nickname || user.username || ""
            }
            if (!this.orderInfo.phone && user.phone) {
                this.orderInfo.phone = user.phone
            }
        }
    }
    open var changeOrderType = ::gen_changeOrderType_fn
    open fun gen_changeOrderType_fn(type: String) {
        this.orderType = type
    }
    open var getOrderTypeLabel = ::gen_getOrderTypeLabel_fn
    open fun gen_getOrderTypeLabel_fn(type: String): String {
        val found = this.typeOptions.find(fun(item: Any): Boolean {
            return item.value === type
        }
        )
        return if (found) {
            found.label
        } else {
            "外卖"
        }
    }
    open var selectAddress = ::gen_selectAddress_fn
    open fun gen_selectAddress_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/address/list?select=true"))
    }
    open var setAddress = ::gen_setAddress_fn
    open fun gen_setAddress_fn(address: Any) {
        this.orderInfo.name = address.contact_name || address.name
        this.orderInfo.phone = address.contact_phone || address.phone
        this.orderInfo.address = address.address || address.detail || ""
    }
    open var fetchDefaultAddress = ::gen_fetchDefaultAddress_fn
    open fun gen_fetchDefaultAddress_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/addresses/default", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success && res.data.data.address) {
                this.setAddress(res.data.data.address)
            }
        }
        ))
    }
    open var getProductImage = ::gen_getProductImage_fn
    open fun gen_getProductImage_fn(item: CartItem1): String {
        if (item.images && item.images.length > 0) {
            return item.images[0]
        }
        return DEFAULT_FOOD_IMAGE
    }
    open var fetchCart = ::gen_fetchCart_fn
    open fun gen_fetchCart_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
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
                        var specs = item.specs || item.options || undefined
                        var notes = item.notes || ""
                    })
                }
                )
            }
        }
        ))
    }
    open var submitOrder = ::gen_submitOrder_fn
    open fun gen_submitOrder_fn(): UTSPromise<Unit> {
        return wrapUTSPromise(suspend w@{
                if (this.orderType === "delivery") {
                    if (!this.orderInfo.name || !this.orderInfo.phone || !this.orderInfo.address) {
                        uni_showToast(ShowToastOptions(title = "请先选择收货地址", icon = "none"))
                        return@w
                    }
                    val minAmount = this.shopInfo.min_order_amount || 0
                    if (minAmount > 0 && this.subtotal < minAmount) {
                        uni_showToast(ShowToastOptions(title = "\u5546\u54C1\u91D1\u989D\u4E0D\u6EE1\u8DB3\u8D77\u9001\u91D1\u989D \xa5" + minAmount, icon = "none"))
                        return@w
                    }
                }
                if (this.orderType === "pickup") {
                    if (!this.orderInfo.name || !this.orderInfo.phone) {
                        uni_showToast(ShowToastOptions(title = "请先选择地址或登录补全联系人", icon = "none"))
                        return@w
                    }
                }
                if (this.cartItems.length === 0) {
                    uni_showToast(ShowToastOptions(title = "购物车为空", icon = "none"))
                    return@w
                }
                val token = uni_getStorageSync("token")
                uni_showLoading(ShowLoadingOptions(title = "提交中..."))
                try {
                    val payload: Any = _uO("order_type" to this.orderType, "notes" to this.orderInfo.remark, "items" to this.cartItems.map(fun(item: CartItem1): Any {
                        val mapped: Any = object : UTSJSONObject() {
                            var product_id = item.product_id
                            var quantity = item.quantity
                        }
                        if (item.specs) {
                            mapped.specs = item.specs
                        }
                        if (item.notes) {
                            mapped.notes = item.notes
                        }
                        return mapped
                    }
                    ), "cart_item_ids" to this.cartItems.map(fun(item: CartItem1): Number {
                        return item.id
                    }
                    ))
                    if (this.orderType === "delivery") {
                        payload.delivery_address = this.orderInfo.address
                        payload.contact_name = this.orderInfo.name
                        payload.contact_phone = this.orderInfo.phone
                    } else if (this.orderType === "pickup") {
                        payload.contact_name = this.orderInfo.name
                        payload.contact_phone = this.orderInfo.phone
                    }
                    val createRes: Any = await(UTSPromise(fun(resolve, reject){
                        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders", method = "POST", header = object : UTSJSONObject() {
                            var Authorization = "Bearer " + token
                            var `Content-Type` = "application/json"
                        }, data = payload, success = resolve, fail = reject))
                    }
                    ))
                    if (!createRes.data?.success) {
                        uni_showToast(ShowToastOptions(title = createRes.data?.message || "下单失败", icon = "none"))
                        return@w
                    }
                    val orderId = createRes.data?.data?.order?.id || createRes.data?.data?.id || createRes.data?.data?.order_id
                    if (!orderId) {
                        uni_showToast(ShowToastOptions(title = "下单成功，但未获取订单ID", icon = "none"))
                        uni_switchTab(SwitchTabOptions(url = "/pages/order/list"))
                        return@w
                    }
                    val payRes: Any = await(UTSPromise(fun(resolve, reject){
                        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + orderId + "/pay", method = "POST", header = object : UTSJSONObject() {
                            var Authorization = "Bearer " + token
                        }, success = resolve, fail = reject))
                    }
                    ))
                    if (payRes.data?.success) {
                        uni_showToast(ShowToastOptions(title = "支付成功", icon = "success"))
                        setTimeout(fun(){
                            uni_redirectTo(RedirectToOptions(url = "/pages/order/detail?id=" + orderId))
                        }, 800)
                    } else {
                        uni_showToast(ShowToastOptions(title = payRes.data?.message || "支付失败", icon = "none"))
                    }
                }
                 catch (e: Throwable) {
                    uni_showToast(ShowToastOptions(title = "网络错误", icon = "none"))
                }
                 finally{
                    uni_hideLoading()
                }
        })
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
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#FFF5F8", "paddingBottom" to "180rpx")), "section" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "paddingTop" to "36rpx", "paddingRight" to "36rpx", "paddingBottom" to "36rpx", "paddingLeft" to "36rpx", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "section-title" to _pS(_uM("fontSize" to "32rpx", "color" to "#5D4E6D", "borderLeftWidth" to "8rpx", "borderLeftStyle" to "solid", "borderLeftColor" to "#FF69B4", "paddingLeft" to "20rpx")), "section-header-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginBottom" to "24rpx")), "arrow" to _pS(_uM("fontSize" to "40rpx", "color" to "#B8A9C9")), "type-options" to _pS(_uM("display" to "flex", "flexDirection" to "row", "marginTop" to "24rpx", "gap" to "16rpx")), "type-chip" to _uM("" to _uM("paddingTop" to "20rpx", "paddingRight" to "28rpx", "paddingBottom" to "20rpx", "paddingLeft" to "28rpx", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "backgroundColor" to "rgba(255,255,255,0.8)", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "boxShadow" to "0 6rpx 16rpx rgba(255, 105, 180, 0.1)", "transitionProperty" to "all", "transitionDuration" to "0.2s"), ".active" to _uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "color" to "#ffffff", "borderTopColor" to "rgba(0,0,0,0)", "borderRightColor" to "rgba(0,0,0,0)", "borderBottomColor" to "rgba(0,0,0,0)", "borderLeftColor" to "rgba(0,0,0,0)", "boxShadow" to "0 8rpx 20rpx rgba(255, 105, 180, 0.3)")), "type-text" to _pS(_uM("fontSize" to "28rpx", "fontWeight" to "700")), "table-input" to _pS(_uM("marginTop" to "20rpx", "width" to "70%", "minWidth" to "400rpx", "paddingTop" to "20rpx", "paddingRight" to "24rpx", "paddingBottom" to "20rpx", "paddingLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.8)", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "boxShadow" to "0 6rpx 16rpx rgba(255, 105, 180, 0.08)", "fontSize" to "30rpx", "color" to "#5D4E6D", "boxSizing" to "border-box")), "address-display" to _pS(_uM("display" to "flex", "flexDirection" to "column", "paddingTop" to "10rpx", "paddingRight" to 0, "paddingBottom" to "10rpx", "paddingLeft" to 0)), "contact-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "marginBottom" to "12rpx")), "contact-name" to _pS(_uM("fontSize" to "34rpx", "color" to "#5D4E6D", "marginRight" to "20rpx")), "contact-phone" to _pS(_uM("fontSize" to "28rpx", "color" to "#888888")), "address-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#666666", "lineHeight" to 1.5)), "address-placeholder" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "placeholder-icon-box" to _pS(_uM("width" to "60rpx", "height" to "60rpx", "backgroundColor" to "#FFF0F5", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "marginRight" to "20rpx")), "placeholder-icon" to _pS(_uM("width" to "32rpx", "height" to "32rpx")), "placeholder-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#B8A9C9")), "order-items" to _pS(_uM("paddingTop" to 0, "paddingRight" to 0, "paddingBottom" to 0, "paddingLeft" to 0)), "order-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "item-image" to _pS(_uM("width" to "140rpx", "height" to "140rpx", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5", "boxShadow" to "0 4rpx 12rpx rgba(0,0,0,0.05)")), "item-info" to _pS(_uM("flex" to 1, "marginLeft" to "24rpx", "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between", "height" to "140rpx")), "item-name" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D", "fontWeight" to "700")), "item-bottom" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center")), "item-price" to _pS(_uM("fontSize" to "32rpx", "color" to "#FF69B4")), "item-qty" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "remark-input" to _pS(_uM("width" to "100%", "height" to "180rpx", "backgroundColor" to "#FFF5F8", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx", "fontSize" to "30rpx", "boxSizing" to "border-box", "color" to "#5D4E6D", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFE4EC", "borderRightColor" to "#FFE4EC", "borderBottomColor" to "#FFE4EC", "borderLeftColor" to "#FFE4EC")), "amount-section" to _pS(_uM("paddingTop" to "30rpx", "paddingRight" to "36rpx", "paddingBottom" to "30rpx", "paddingLeft" to "36rpx")), "amount-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "paddingTop" to "16rpx", "paddingRight" to 0, "paddingBottom" to "16rpx", "paddingLeft" to 0)), "amount-label" to _pS(_uM("fontSize" to "30rpx", "color" to "#B8A9C9")), "amount-value" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D")), "hint-row" to _pS(_uM("backgroundColor" to "#FFF8F0", "marginTop" to "10rpx", "marginRight" to "-20rpx", "marginBottom" to "10rpx", "marginLeft" to "-20rpx", "paddingTop" to "12rpx", "paddingRight" to "20rpx", "paddingBottom" to "12rpx", "paddingLeft" to "20rpx", "borderTopLeftRadius" to "12rpx", "borderTopRightRadius" to "12rpx", "borderBottomRightRadius" to "12rpx", "borderBottomLeftRadius" to "12rpx")), "hint-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9")), "hint-status" to _uM("" to _uM("fontSize" to "26rpx"), ".success" to _uM("color" to "#52c41a"), ".warning" to _uM("color" to "#faad14")), "total-row" to _pS(_uM("marginTop" to "20rpx", "paddingTop" to "24rpx", "borderTopWidth" to 2, "borderTopStyle" to "dashed", "borderTopColor" to "#FFE4EC")), "total-value" to _pS(_uM("fontSize" to "40rpx", "color" to "#FF69B4")), "submit-bar" to _pS(_uM("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "justifyContent" to "space-between", "paddingTop" to "24rpx", "paddingRight" to "36rpx", "paddingBottom" to "24rpx", "paddingLeft" to "36rpx", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.95)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 -4rpx 30rpx rgba(255, 105, 180, 0.2)", "zIndex" to 100, "backdropFilter" to "blur(10px)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.5)", "borderRightColor" to "rgba(255,255,255,0.5)", "borderBottomColor" to "rgba(255,255,255,0.5)", "borderLeftColor" to "rgba(255,255,255,0.5)")), "total-box" to _pS(_uM("display" to "flex", "flexDirection" to "row")), "total-label" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D")), "total-amount" to _pS(_uM("fontSize" to "44rpx", "color" to "#FF69B4", "marginLeft" to "10rpx")), "submit-btn" to _pS(_uM("paddingTop" to "28rpx", "paddingRight" to "64rpx", "paddingBottom" to "28rpx", "paddingLeft" to "64rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.4)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.95)")), "submit-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#ffffff", "letterSpacing" to 1)), "@TRANSITION" to _uM("type-chip" to _uM("property" to "all", "duration" to "0.2s"), "submit-btn" to _uM("property" to "transform", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
