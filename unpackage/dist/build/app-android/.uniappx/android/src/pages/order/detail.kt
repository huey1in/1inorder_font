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
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesOrderDetail : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            if (options?.id) {
                this.orderId = Number(options.id)
                this.fetchDetail()
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "section header"), _uA(
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "订单号"),
                    _cE("text", _uM("class" to "value mono"), _tD(_ctx.order.order_number || "-"), 1)
                )),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "状态"),
                    _cE("text", _uM("class" to _nC(_uA(
                        "value status",
                        "status-" + _ctx.order.status
                    ))), _tD(_ctx.statusText), 3)
                )),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "类型"),
                    _cE("text", _uM("class" to "value"), _tD(_ctx.orderTypeLabel), 1)
                )),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "下单时间"),
                    _cE("text", _uM("class" to "value"), _tD(_ctx.order.created_at || "-"), 1)
                ))
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "联系信息"),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "联系人"),
                    _cE("text", _uM("class" to "value"), _tD(_ctx.order.contact_name || "-"), 1)
                )),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "手机号"),
                    _cE("text", _uM("class" to "value"), _tD(_ctx.order.contact_phone || "-"), 1)
                )),
                if (_ctx.order.order_type === "delivery") {
                    _cE("view", _uM("key" to 0, "class" to "row"), _uA(
                        _cE("text", _uM("class" to "label"), "收货地址"),
                        _cE("text", _uM("class" to "value"), _tD(_ctx.order.delivery_address || "-"), 1)
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(_ctx.order.order_type === "pickup" && _ctx.order.pickup_code)) {
                    _cE("view", _uM("key" to 1, "class" to "row"), _uA(
                        _cE("text", _uM("class" to "label"), "取餐码"),
                        _cE("text", _uM("class" to "value strong"), _tD(_ctx.order.pickup_code), 1)
                    ))
                } else {
                    _cC("v-if", true)
                }
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "商品清单"),
                _cE(Fragment, null, RenderHelpers.renderList(_ctx.order.items, fun(item, __key, __index, _cached): Any {
                    return _cE("view", _uM("class" to "item", "key" to item.id), _uA(
                        _cE("image", _uM("class" to "item-img", "src" to _ctx.getProductImage(item), "mode" to "aspectFill"), null, 8, _uA(
                            "src"
                        )),
                        _cE("view", _uM("class" to "item-info"), _uA(
                            _cE("text", _uM("class" to "item-name"), _tD(item.product_name || "商品"), 1),
                            if (isTrue(item.specs)) {
                                _cE("text", _uM("key" to 0, "class" to "item-spec"), _tD(_ctx.specString(item.specs)), 1)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            if (isTrue(item.notes)) {
                                _cE("text", _uM("key" to 1, "class" to "item-note"), _tD(item.notes), 1)
                            } else {
                                _cC("v-if", true)
                            }
                            ,
                            _cE("view", _uM("class" to "item-bottom"), _uA(
                                _cE("text", _uM("class" to "item-price"), "￥" + _tD(item.price), 1),
                                _cE("text", _uM("class" to "item-qty"), "x" + _tD(item.quantity), 1)
                            ))
                        ))
                    ))
                }
                ), 128)
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("text", _uM("class" to "section-title"), "金额信息"),
                _cE("view", _uM("class" to "row"), _uA(
                    _cE("text", _uM("class" to "label"), "商品金额"),
                    _cE("text", _uM("class" to "value"), "￥" + _tD(_ctx.order.total_amount || 0), 1)
                )),
                if (_ctx.order.order_type === "delivery") {
                    _cE("view", _uM("key" to 0, "class" to "row"), _uA(
                        _cE("text", _uM("class" to "label"), "配送费"),
                        _cE("text", _uM("class" to "value"), "￥" + _tD(_ctx.order.delivery_fee || 0), 1)
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                _cE("view", _uM("class" to "row total"), _uA(
                    _cE("text", _uM("class" to "label"), "合计"),
                    _cE("text", _uM("class" to "value total-amount"), "￥" + _tD(_ctx.order.total_amount || 0), 1)
                ))
            )),
            if (isTrue(_ctx.order.notes)) {
                _cE("view", _uM("key" to 0, "class" to "section"), _uA(
                    _cE("text", _uM("class" to "section-title"), "备注"),
                    _cE("text", _uM("class" to "remark"), _tD(_ctx.order.notes), 1)
                ))
            } else {
                _cC("v-if", true)
            }
        ))
    }
    open var orderId: Number by `$data`
    open var order: Any by `$data`
    open var loading: Boolean by `$data`
    open var statusText: String by `$data`
    open var orderTypeLabel: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("orderId" to 0, "order" to object : UTSJSONObject() {
            var items = _uA()
        } as Any, "loading" to false, "statusText" to computed<String>(fun(): String {
            val map = Record(pending = "待确认", confirmed = "已确认", preparing = "制作中", ready = "待取餐", delivering = "配送中", delivered = "已完成", cancelled = "已取消")
            return map[this.order?.status] || this.order?.status || ""
        }
        ), "orderTypeLabel" to computed<String>(fun(): String {
            val map = Record(delivery = "外卖", pickup = "自取", dine_in = "堂食")
            return map[this.order?.order_type] || this.order?.order_type || ""
        }
        ))
    }
    open var getProductImage = ::gen_getProductImage_fn
    open fun gen_getProductImage_fn(item: Any): String {
        if (item.images && item.images.length > 0) {
            return item.images[0]
        }
        return DEFAULT_FOOD_IMAGE
    }
    open var specString = ::gen_specString_fn
    open fun gen_specString_fn(specs: Any): String {
        if (!specs) {
            return ""
        }
        if (UTSAndroid.`typeof`(specs) === "string") {
            return specs as String
        }
        return Object.entries(specs).map(fun(ref): String {
            var k = ref[0]
            var v = ref[1]
            return "" + k + ":" + v
        }
        ).join(" ")
    }
    open var fetchDetail = ::gen_fetchDetail_fn
    open fun gen_fetchDetail_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        this.loading = true
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + this.orderId, method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success && res.data.data) {
                val data = res.data.data
                this.order = data.order || data
            } else {
                uni_showToast(ShowToastOptions(title = res.data?.message || "获取详情失败", icon = "none"))
            }
        }
        , fail = fun(_){
            uni_showToast(ShowToastOptions(title = "网络错误", icon = "none"))
        }
        , complete = fun(_){
            this.loading = false
        }
        ))
    }
    open var payOrder = ::gen_payOrder_fn
    open fun gen_payOrder_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_showLoading(ShowLoadingOptions(title = "支付中..."))
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + this.orderId + "/pay", method = "POST", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data?.success) {
                uni_showToast(ShowToastOptions(title = "支付成功", icon = "success"))
                this.fetchDetail()
            } else {
                uni_showToast(ShowToastOptions(title = res.data?.message || "支付失败", icon = "none"))
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
    open var deleteOrder = ::gen_deleteOrder_fn
    open fun gen_deleteOrder_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_showModal(ShowModalOptions(title = "删除订单", content = "确定删除该订单吗？", success = fun(res){
            if (!res.confirm) {
                return
            }
            uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + this.orderId, method = "DELETE", header = object : UTSJSONObject() {
                var Authorization = "Bearer " + token
            }, success = fun(resp: Any){
                if (resp.data?.success) {
                    uni_showToast(ShowToastOptions(title = "已删除", icon = "success"))
                    setTimeout(fun(){
                        uni_switchTab(SwitchTabOptions(url = "/pages/order/list"))
                    }, 800)
                } else {
                    uni_showToast(ShowToastOptions(title = resp.data?.message || "删除失败", icon = "none"))
                }
            }
            , fail = fun(_){
                uni_showToast(ShowToastOptions(title = "网络错误", icon = "none"))
            }
            ))
        }
        ))
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingBottom" to "140rpx")), "section" to _pS(_uM("backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.95)", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "borderTopLeftRadius" to "36rpx", "borderTopRightRadius" to "36rpx", "borderBottomRightRadius" to "36rpx", "borderBottomLeftRadius" to "36rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.12)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.6)", "borderRightColor" to "rgba(255,255,255,0.6)", "borderBottomColor" to "rgba(255,255,255,0.6)", "borderLeftColor" to "rgba(255,255,255,0.6)")), "section-title" to _pS(_uM("fontSize" to "32rpx", "color" to "#5D4E6D", "borderLeftWidth" to "8rpx", "borderLeftStyle" to "solid", "borderLeftColor" to "#FF69B4", "paddingLeft" to "18rpx", "marginBottom" to "18rpx")), "row" to _uM(".header " to _uM("marginBottom" to "12rpx"), "" to _uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "12rpx")), "label" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "value" to _uM("" to _uM("fontSize" to "28rpx", "color" to "#5D4E6D", "textAlign" to "right"), ".mono" to _uM("fontFamily" to "monospace"), ".status" to _uM("color" to "#FF69B4"), ".strong" to _uM("color" to "#FF69B4")), "status-pending" to _pS(_uM("!color" to "#FF9800")), "status-confirmed" to _pS(_uM("!color" to "#4CAF50")), "status-preparing" to _pS(_uM("!color" to "#FF9800")), "status-ready" to _pS(_uM("!color" to "#4CAF50")), "status-delivering" to _pS(_uM("!color" to "#2196F3")), "status-delivered" to _pS(_uM("!color" to "#9C27B0")), "status-cancelled" to _pS(_uM("!color" to "#9E9E9E")), "address-display" to _pS(_uM("marginTop" to "10rpx")), "item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "marginTop" to "20rpx")), "item-img" to _pS(_uM("width" to "120rpx", "height" to "120rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5")), "item-info" to _pS(_uM("flex" to 1, "marginLeft" to "20rpx")), "item-name" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D", "fontWeight" to "700")), "item-spec" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9", "marginTop" to "6rpx")), "item-note" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9", "marginTop" to "6rpx")), "item-bottom" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "12rpx")), "item-price" to _pS(_uM("fontSize" to "30rpx", "color" to "#FF69B4")), "item-qty" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9")), "total" to _pS(_uM("marginTop" to "14rpx")), "total-amount" to _pS(_uM("color" to "#FF69B4")), "remark" to _pS(_uM("fontSize" to "28rpx", "color" to "#5D4E6D")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
