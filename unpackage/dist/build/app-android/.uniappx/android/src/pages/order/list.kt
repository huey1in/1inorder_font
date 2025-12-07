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
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.removeStorageSync as uni_removeStorageSync
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesOrderList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onPageShow(fun() {
            val status = uni_getStorageSync("orderStatus")
            if (status) {
                this.currentStatus = status
                uni_removeStorageSync("orderStatus")
                this.page = 1
                this.orders = _uA()
                this.noMore = false
            }
            this.fetchOrders()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "status-tabs-wrapper"), _uA(
                _cE("scroll-view", _uM("class" to "status-tabs", "direction" to "horizontal", "scroll-x" to true, "show-scrollbar" to false), _uA(
                    _cE("view", _uM("class" to "tabs-inner"), _uA(
                        _cE(Fragment, null, RenderHelpers.renderList(_ctx.statusTabs, fun(tab, __key, __index, _cached): Any {
                            return _cE("view", _uM("key" to tab.value, "class" to _nC(_uA(
                                "tab-item",
                                if (_ctx.currentStatus === tab.value) {
                                    "active"
                                } else {
                                    ""
                                }
                            )), "onClick" to fun(){
                                _ctx.switchStatus(tab.value)
                            }
                            ), _uA(
                                _cE("text", _uM("class" to "tab-text"), _tD(tab.label), 1)
                            ), 10, _uA(
                                "onClick"
                            ))
                        }
                        ), 128)
                    ))
                ))
            )),
            _cE("scroll-view", _uM("class" to "order-list", "scroll-y" to "", "onScrolltolower" to _ctx.loadMore), _uA(
                _cE(Fragment, null, RenderHelpers.renderList(_ctx.displayOrders, fun(order, __key, __index, _cached): Any {
                    return _cE("view", _uM("class" to "order-card", "key" to order.id, "onClick" to fun(){
                        _ctx.goDetail(order.id)
                    }
                    ), _uA(
                        _cE("view", _uM("class" to "order-header"), _uA(
                            _cE("text", _uM("class" to "order-no"), "订单号: " + _tD(order.order_number), 1),
                            _cE("text", _uM("class" to _nC(_uA(
                                "order-status",
                                "status-" + order.status
                            ))), _tD(_ctx.getStatusText(order)), 3)
                        )),
                        _cE("view", _uM("class" to "order-items"), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(order.items, fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("class" to "order-item", "key" to item.id), _uA(
                                    _cE("image", _uM("class" to "item-image", "src" to _ctx.getProductImage(item), "mode" to "aspectFill"), null, 8, _uA(
                                        "src"
                                    )),
                                    _cE("view", _uM("class" to "item-info"), _uA(
                                        _cE("text", _uM("class" to "item-name"), _tD(item.product_name || "商品"), 1),
                                        _cE("text", _uM("class" to "item-qty"), "x" + _tD(item.quantity), 1)
                                    )),
                                    _cE("text", _uM("class" to "item-price"), "¥" + _tD(item.price), 1)
                                ))
                            }
                            ), 128)
                        )),
                        _cE("view", _uM("class" to "order-footer"), _uA(
                            _cE("text", _uM("class" to "order-total"), _uA(
                                "共" + _tD(order.items?.length || 0) + "件商品，合计: ",
                                _cE("text", _uM("class" to "total-amount"), "¥" + _tD(order.total_amount), 1)
                            )),
                            _cE("view", _uM("class" to "order-actions"), _uA(
                                if (isTrue(order.status === "pending" && order.payment_status !== "paid")) {
                                    _cE("view", _uM("key" to 0, "class" to "action-btn", "onClick" to withModifiers(fun(){
                                        _ctx.cancelOrder(order)
                                    }, _uA(
                                        "stop"
                                    ))), _uA(
                                        _cE("text", _uM("class" to "btn-text"), "取消订单")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(order.status === "pending" && order.payment_status !== "paid")) {
                                    _cE("view", _uM("key" to 1, "class" to "action-btn primary", "onClick" to withModifiers(fun(){
                                        _ctx.payOrder(order)
                                    }, _uA(
                                        "stop"
                                    ))), _uA(
                                        _cE("text", _uM("class" to "btn-text-primary"), "去支付")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                                ,
                                if (isTrue(_ctx.showDelete(order))) {
                                    _cE("view", _uM("key" to 2, "class" to "action-btn danger", "onClick" to withModifiers(fun(){
                                        _ctx.deleteOrder(order)
                                    }, _uA(
                                        "stop"
                                    ))), _uA(
                                        _cE("text", _uM("class" to "btn-text"), "删除订单")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                } else {
                                    _cC("v-if", true)
                                }
                            ))
                        ))
                    ), 8, _uA(
                        "onClick"
                    ))
                }
                ), 128),
                if (isTrue(_ctx.displayOrders.length === 0 && !_ctx.loading)) {
                    _cE("view", _uM("key" to 0, "class" to "empty"), _uA(
                        _cE("image", _uM("class" to "empty-icon", "src" to "/static/icons/icon-order.svg", "mode" to "aspectFit")),
                        _cE("text", _uM("class" to "empty-text"), "暂无订单~")
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(_ctx.loading)) {
                    _cE("view", _uM("key" to 1, "class" to "loading-more"), _uA(
                        _cE("text", null, "加载中...")
                    ))
                } else {
                    _cC("v-if", true)
                }
                ,
                if (isTrue(!_ctx.loading && _ctx.noMore && _ctx.orders.length > 0)) {
                    _cE("view", _uM("key" to 2, "class" to "no-more"), _uA(
                        _cE("text", null, "没有更多了")
                    ))
                } else {
                    _cC("v-if", true)
                }
            ), 40, _uA(
                "onScrolltolower"
            ))
        ))
    }
    open var statusTabs: UTSArray<UTSJSONObject> by `$data`
    open var currentStatus: String by `$data`
    open var orders: UTSArray<Order> by `$data`
    open var loading: Boolean by `$data`
    open var noMore: Boolean by `$data`
    open var page: Number by `$data`
    open var displayOrders: UTSArray<Order> by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("statusTabs" to _uA(
            object : UTSJSONObject() {
                var label = "全部"
                var value = ""
            },
            object : UTSJSONObject() {
                var label = "已确认"
                var value = "confirmed"
            },
            object : UTSJSONObject() {
                var label = "制作中"
                var value = "preparing"
            },
            object : UTSJSONObject() {
                var label = "待取餐"
                var value = "ready"
            },
            object : UTSJSONObject() {
                var label = "配送中"
                var value = "delivering"
            },
            object : UTSJSONObject() {
                var label = "已完成"
                var value = "delivered"
            },
            object : UTSJSONObject() {
                var label = "已取消"
                var value = "cancelled"
            }
        ), "currentStatus" to "", "orders" to _uA<Order>(), "loading" to false, "noMore" to false, "page" to 1, "displayOrders" to computed<UTSArray<Order>>(fun(): UTSArray<Order> {
            if (this.currentStatus === "") {
                return this.orders
            }
            if (this.currentStatus === "pending") {
                return this.orders.filter(fun(o: Any): Boolean {
                    return o.status === "pending" && o.payment_status !== "paid"
                }
                )
            }
            return this.orders.filter(fun(o: Any): Boolean {
                return o.status === this.currentStatus
            }
            )
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
    open var fetchOrders = ::gen_fetchOrders_fn
    open fun gen_fetchOrders_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            return
        }
        this.loading = true
        val requestData: Any = _uO("page" to this.page, "limit" to 10)
        if (this.currentStatus !== "") {
            requestData.status = this.currentStatus
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/my", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, data = requestData, success = fun(res: Any){
            if (res.data.success && res.data.data.orders) {
                val newOrders = res.data.data.orders
                if (this.page === 1) {
                    this.orders = newOrders
                } else {
                    this.orders = this.orders.concat(newOrders)
                }
                this.noMore = newOrders.length < 10
            }
        }
        , complete = fun(_){
            this.loading = false
        }
        ))
    }
    open var switchStatus = ::gen_switchStatus_fn
    open fun gen_switchStatus_fn(status: String) {
        this.currentStatus = status
        this.page = 1
        this.noMore = false
        this.orders = _uA()
        this.fetchOrders()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (this.loading || this.noMore) {
            return
        }
        this.page++
        this.fetchOrders()
    }
    open var getStatusText = ::gen_getStatusText_fn
    open fun gen_getStatusText_fn(order: Any): String {
        val map = Record(pending = "待确认", confirmed = "已确认", preparing = "制作中", ready = "待取餐", delivering = "配送中", delivered = "已完成", cancelled = "已取消")
        return map[order?.status] || order?.status || ""
    }
    open var showDelete = ::gen_showDelete_fn
    open fun gen_showDelete_fn(order: Any): Boolean {
        return order?.status === "cancelled" || order?.status === "delivered"
    }
    open var goDetail = ::gen_goDetail_fn
    open fun gen_goDetail_fn(id: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/order/detail?id=" + id))
    }
    open var cancelOrder = ::gen_cancelOrder_fn
    open fun gen_cancelOrder_fn(order: Order) {
        uni_showModal(ShowModalOptions(title = "取消订单", content = "确定要取消该订单吗？", success = fun(res){
            if (res.confirm) {
                val token = uni_getStorageSync("token")
                uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + order.id + "/cancel", method = "POST", header = object : UTSJSONObject() {
                    var Authorization = "Bearer " + token
                }, success = fun(res: Any){
                    if (res.data.success) {
                        uni_showToast(ShowToastOptions(title = "已取消", icon = "success"))
                        this.page = 1
                        this.fetchOrders()
                    }
                }
                ))
            }
        }
        ))
    }
    open var payOrder = ::gen_payOrder_fn
    open fun gen_payOrder_fn(order: Order) {
        uni_showToast(ShowToastOptions(title = "支付功能开发中", icon = "none"))
    }
    open var reorder = ::gen_reorder_fn
    open fun gen_reorder_fn(order: Order) {
        uni_showToast(ShowToastOptions(title = "已加入购物车", icon = "success"))
        uni_switchTab(SwitchTabOptions(url = "/pages/cart/cart"))
    }
    open var deleteOrder = ::gen_deleteOrder_fn
    open fun gen_deleteOrder_fn(order: Order) {
        uni_showModal(ShowModalOptions(title = "删除订单", content = "确定删除该订单吗？", success = fun(res){
            if (!res.confirm) {
                return
            }
            val token = uni_getStorageSync("token")
            uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/orders/" + order.id, method = "DELETE", header = object : UTSJSONObject() {
                var Authorization = "Bearer " + token
            }, success = fun(resp: Any){
                if (resp.data?.success) {
                    uni_showToast(ShowToastOptions(title = "已删除", icon = "success"))
                    this.page = 1
                    this.orders = _uA()
                    this.noMore = false
                    this.fetchOrders()
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
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#FFF5F8")), "status-tabs-wrapper" to _pS(_uM("width" to "100%", "backgroundColor" to "rgba(255,255,255,0.9)", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "backdropFilter" to "blur(10px)", "top" to 0, "zIndex" to 10)), "status-tabs" to _pS(_uM("width" to "100%", "height" to "100rpx")), "tabs-inner" to _pS(_uM("display" to "flex", "flexDirection" to "row", "flexWrap" to "nowrap", "height" to "100rpx", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx")), "tab-item" to _uM("" to _uM("paddingTop" to 0, "paddingRight" to "32rpx", "paddingBottom" to 0, "paddingLeft" to "32rpx", "height" to "100rpx", "display" to "flex", "alignItems" to "center", "justifyContent" to "center", "position" to "relative"), ".active::after" to _uM("content" to "''", "position" to "absolute", "bottom" to 0, "left" to "50%", "transform" to "translateX(-50%)", "width" to "40rpx", "height" to "6rpx", "backgroundColor" to "#FF69B4", "borderTopLeftRadius" to "6rpx", "borderTopRightRadius" to "6rpx", "borderBottomRightRadius" to "6rpx", "borderBottomLeftRadius" to "6rpx")), "tab-text" to _uM("" to _uM("fontSize" to "28rpx", "color" to "#B8A9C9", "transitionProperty" to "all", "transitionDuration" to "0.3s"), ".tab-item.active " to _uM("color" to "#FF69B4", "fontSize" to "30rpx")), "order-list" to _pS(_uM("flex" to 1, "paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx")), "order-card" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "marginBottom" to "24rpx", "overflow" to "hidden", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.6)", "borderRightColor" to "rgba(255,255,255,0.6)", "borderBottomColor" to "rgba(255,255,255,0.6)", "borderLeftColor" to "rgba(255,255,255,0.6)")), "order-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "24rpx", "paddingRight" to "30rpx", "paddingBottom" to "24rpx", "paddingLeft" to "30rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "backgroundColor" to "rgba(255,240,245,0.5)")), "order-no" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9", "fontFamily" to "monospace")), "order-status" to _pS(_uM("fontSize" to "26rpx", "paddingTop" to "6rpx", "paddingRight" to "16rpx", "paddingBottom" to "6rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "backgroundColor" to "#FFFFFF")), "status-pending" to _pS(_uM("color" to "#FF69B4", "backgroundColor" to "#FFF0F5")), "status-confirmed" to _pS(_uM("color" to "#4CAF50", "backgroundColor" to "#E8F5E9")), "status-preparing" to _pS(_uM("color" to "#FF9800", "backgroundColor" to "#FFF3E0")), "status-ready" to _pS(_uM("color" to "#4CAF50", "backgroundColor" to "#E8F5E9")), "status-delivering" to _pS(_uM("color" to "#2196F3", "backgroundColor" to "#E3F2FD")), "status-delivered" to _pS(_uM("color" to "#B8A9C9", "backgroundColor" to "#F3E5F5")), "status-cancelled" to _pS(_uM("color" to "#D4C4E3", "backgroundColor" to "#F5F5F5")), "order-items" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "20rpx", "paddingLeft" to "30rpx")), "order-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "16rpx", "paddingRight" to 0, "paddingBottom" to "16rpx", "paddingLeft" to 0)), "item-image" to _pS(_uM("width" to "120rpx", "height" to "120rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5", "boxShadow" to "0 4rpx 12rpx rgba(0,0,0,0.05)")), "item-info" to _pS(_uM("flex" to 1, "marginLeft" to "24rpx")), "item-name" to _pS(_uM("fontSize" to "30rpx", "color" to "#5D4E6D")), "item-qty" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9", "marginTop" to "10rpx")), "item-price" to _pS(_uM("fontSize" to "30rpx", "color" to "#FF69B4")), "order-footer" to _pS(_uM("paddingTop" to "24rpx", "paddingRight" to "30rpx", "paddingBottom" to "24rpx", "paddingLeft" to "30rpx", "borderTopWidth" to 1, "borderTopStyle" to "solid", "borderTopColor" to "rgba(255,228,236,0.6)", "display" to "flex", "flexDirection" to "column", "alignItems" to "flex-end")), "order-total" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "total-amount" to _pS(_uM("color" to "#FF69B4", "fontSize" to "34rpx", "marginLeft" to "10rpx")), "order-actions" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "flex-end", "marginTop" to "24rpx")), "action-btn" to _uM("" to _uM("paddingTop" to "16rpx", "paddingRight" to "36rpx", "paddingBottom" to "16rpx", "paddingLeft" to "36rpx", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "marginLeft" to "20rpx", "backgroundColor" to "#FFF5F8", "transitionProperty" to "all", "transitionDuration" to "0.2s", "backgroundColor:active" to "#FFE4EC"), ".primary" to _uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopColor" to "#FF69B4", "borderRightColor" to "#FF69B4", "borderBottomColor" to "#FF69B4", "borderLeftColor" to "#FF69B4", "boxShadow" to "0 4rpx 12rpx rgba(255, 105, 180, 0.3)"), ".primary:active" to _uM("transform" to "scale(0.95)")), "btn-text" to _pS(_uM("fontSize" to "28rpx", "color" to "#FF69B4")), "btn-text-primary" to _pS(_uM("fontSize" to "28rpx", "color" to "#ffffff")), "empty" to _pS(_uM("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to "120rpx", "paddingRight" to 0, "paddingBottom" to "120rpx", "paddingLeft" to 0)), "empty-icon" to _pS(_uM("width" to "200rpx", "height" to "200rpx", "opacity" to 0.9, "filter" to "drop-shadow(0 10rpx 20rpx rgba(255, 105, 180, 0.2))", "animation" to "float 3s ease-in-out infinite")), "empty-text" to _pS(_uM("fontSize" to "30rpx", "color" to "#B8A9C9", "marginTop" to "30rpx")), "loading-more" to _pS(_uM("textAlign" to "center", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "color" to "#B8A9C9", "fontSize" to "26rpx")), "no-more" to _pS(_uM("textAlign" to "center", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "color" to "#B8A9C9", "fontSize" to "26rpx")), "@FONT-FACE" to _uM("0" to _uM()), "@TRANSITION" to _uM("tab-text" to _uM("property" to "all", "duration" to "0.3s"), "action-btn" to _uM("property" to "all", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
