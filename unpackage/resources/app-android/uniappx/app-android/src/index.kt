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
import io.dcloud.uniapp.extapi.exit as uni_exit
import io.dcloud.uniapp.extapi.showToast as uni_showToast
val runBlock1 = run {
    __uniConfig.getAppStyles = fun(): Map<String, Map<String, Map<String, Any>>> {
        return GenApp.styles
    }
}
var firstBackTime: Number = 0
open class GenApp : BaseApp {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {
        onLaunch(fun(_: OnLaunchOptions) {
            console.log("App Launch")
        }
        , __ins)
        onAppShow(fun(_: OnShowOptions) {
            console.log("App Show")
        }
        , __ins)
        onAppHide(fun() {
            console.log("App Hide")
        }
        , __ins)
        onLastPageBackPress(fun() {
            console.log("App LastPageBackPress")
            if (firstBackTime == 0) {
                uni_showToast(ShowToastOptions(title = "再按一次退出应用", position = "bottom"))
                firstBackTime = Date.now()
                setTimeout(fun(){
                    firstBackTime = 0
                }, 2000)
            } else if (Date.now() - firstBackTime < 2000) {
                firstBackTime = Date.now()
                uni_exit(null)
            }
        }
        , __ins)
        onExit(fun() {
            console.log("App Exit")
        }
        , __ins)
    }
    companion object {
        val styles: Map<String, Map<String, Map<String, Any>>> by lazy {
            _nCS(_uA(
                styles0
            ))
        }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return _uM("uni-row" to _pS(_uM("flexDirection" to "row")), "uni-column" to _pS(_uM("flexDirection" to "column")))
            }
    }
}
val GenAppClass = CreateVueAppComponent(GenApp::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "app", name = "", inheritAttrs = true, inject = Map(), props = Map(), propsNeedCastKeys = _uA(), emits = Map(), components = Map(), styles = GenApp.styles)
}
, fun(instance): GenApp {
    return GenApp(instance)
}
)
val BASE_URL = "https://order.yinxh.fun/api"
val DEFAULT_FOOD_IMAGE = "https://order.yinxh.fun/uploads/products/default.svg"
val DEFAULT_AVATAR_IMAGE = "https://order.yinxh.fun/uploads/avatars/default.svg"
val GenPagesIndexIndexClass = CreateVueComponent(GenPagesIndexIndex::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesIndexIndex.inheritAttrs, inject = GenPagesIndexIndex.inject, props = GenPagesIndexIndex.props, propsNeedCastKeys = GenPagesIndexIndex.propsNeedCastKeys, emits = GenPagesIndexIndex.emits, components = GenPagesIndexIndex.components, styles = GenPagesIndexIndex.styles)
}
, fun(instance, renderer): GenPagesIndexIndex {
    return GenPagesIndexIndex(instance, renderer)
}
)
open class CartItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var product_id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var images: UTSArray<String>,
    @JsonNotNull
    open var selected: Boolean = false,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CartItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CartItemReactiveObject : CartItem, IUTSReactive<CartItem> {
    override var __v_raw: CartItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CartItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, product_id = __v_raw.product_id, name = __v_raw.name, price = __v_raw.price, quantity = __v_raw.quantity, images = __v_raw.images, selected = __v_raw.selected) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CartItemReactiveObject {
        return CartItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var product_id: Number
        get() {
            return _tRG(__v_raw, "product_id", __v_raw.product_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_id")) {
                return
            }
            val oldValue = __v_raw.product_id
            __v_raw.product_id = value
            _tRS(__v_raw, "product_id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var price: Number
        get() {
            return _tRG(__v_raw, "price", __v_raw.price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            _tRS(__v_raw, "price", oldValue, value)
        }
    override var quantity: Number
        get() {
            return _tRG(__v_raw, "quantity", __v_raw.quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            _tRS(__v_raw, "quantity", oldValue, value)
        }
    override var images: UTSArray<String>
        get() {
            return _tRG(__v_raw, "images", __v_raw.images, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("images")) {
                return
            }
            val oldValue = __v_raw.images
            __v_raw.images = value
            _tRS(__v_raw, "images", oldValue, value)
        }
    override var selected: Boolean
        get() {
            return _tRG(__v_raw, "selected", __v_raw.selected, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("selected")) {
                return
            }
            val oldValue = __v_raw.selected
            __v_raw.selected = value
            _tRS(__v_raw, "selected", oldValue, value)
        }
}
val GenPagesCartCartClass = CreateVueComponent(GenPagesCartCart::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesCartCart.inheritAttrs, inject = GenPagesCartCart.inject, props = GenPagesCartCart.props, propsNeedCastKeys = GenPagesCartCart.propsNeedCastKeys, emits = GenPagesCartCart.emits, components = GenPagesCartCart.components, styles = GenPagesCartCart.styles)
}
, fun(instance, renderer): GenPagesCartCart {
    return GenPagesCartCart(instance, renderer)
}
)
val GenPagesMyMyClass = CreateVueComponent(GenPagesMyMy::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesMyMy.inheritAttrs, inject = GenPagesMyMy.inject, props = GenPagesMyMy.props, propsNeedCastKeys = GenPagesMyMy.propsNeedCastKeys, emits = GenPagesMyMy.emits, components = GenPagesMyMy.components, styles = GenPagesMyMy.styles)
}
, fun(instance, renderer): GenPagesMyMy {
    return GenPagesMyMy(instance, renderer)
}
)
val GenPagesLoginLoginClass = CreateVueComponent(GenPagesLoginLogin::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesLoginLogin.inheritAttrs, inject = GenPagesLoginLogin.inject, props = GenPagesLoginLogin.props, propsNeedCastKeys = GenPagesLoginLogin.propsNeedCastKeys, emits = GenPagesLoginLogin.emits, components = GenPagesLoginLogin.components, styles = GenPagesLoginLogin.styles)
}
, fun(instance, renderer): GenPagesLoginLogin {
    return GenPagesLoginLogin(instance, renderer)
}
)
val GenPagesProductDetailClass = CreateVueComponent(GenPagesProductDetail::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesProductDetail.inheritAttrs, inject = GenPagesProductDetail.inject, props = GenPagesProductDetail.props, propsNeedCastKeys = GenPagesProductDetail.propsNeedCastKeys, emits = GenPagesProductDetail.emits, components = GenPagesProductDetail.components, styles = GenPagesProductDetail.styles)
}
, fun(instance, renderer): GenPagesProductDetail {
    return GenPagesProductDetail(instance, renderer)
}
)
open class CartItem1 (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var product_id: Number,
    @JsonNotNull
    open var name: String,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var images: UTSArray<String>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return CartItem1ReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class CartItem1ReactiveObject : CartItem1, IUTSReactive<CartItem1> {
    override var __v_raw: CartItem1
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: CartItem1, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, product_id = __v_raw.product_id, name = __v_raw.name, price = __v_raw.price, quantity = __v_raw.quantity, images = __v_raw.images) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): CartItem1ReactiveObject {
        return CartItem1ReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var product_id: Number
        get() {
            return _tRG(__v_raw, "product_id", __v_raw.product_id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_id")) {
                return
            }
            val oldValue = __v_raw.product_id
            __v_raw.product_id = value
            _tRS(__v_raw, "product_id", oldValue, value)
        }
    override var name: String
        get() {
            return _tRG(__v_raw, "name", __v_raw.name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("name")) {
                return
            }
            val oldValue = __v_raw.name
            __v_raw.name = value
            _tRS(__v_raw, "name", oldValue, value)
        }
    override var price: Number
        get() {
            return _tRG(__v_raw, "price", __v_raw.price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            _tRS(__v_raw, "price", oldValue, value)
        }
    override var quantity: Number
        get() {
            return _tRG(__v_raw, "quantity", __v_raw.quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            _tRS(__v_raw, "quantity", oldValue, value)
        }
    override var images: UTSArray<String>
        get() {
            return _tRG(__v_raw, "images", __v_raw.images, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("images")) {
                return
            }
            val oldValue = __v_raw.images
            __v_raw.images = value
            _tRS(__v_raw, "images", oldValue, value)
        }
}
val GenPagesOrderCreateClass = CreateVueComponent(GenPagesOrderCreate::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderCreate.inheritAttrs, inject = GenPagesOrderCreate.inject, props = GenPagesOrderCreate.props, propsNeedCastKeys = GenPagesOrderCreate.propsNeedCastKeys, emits = GenPagesOrderCreate.emits, components = GenPagesOrderCreate.components, styles = GenPagesOrderCreate.styles)
}
, fun(instance, renderer): GenPagesOrderCreate {
    return GenPagesOrderCreate(instance, renderer)
}
)
open class OrderItem (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var quantity: Number,
    @JsonNotNull
    open var price: Number,
    @JsonNotNull
    open var product_name: String,
    @JsonNotNull
    open var images: UTSArray<String>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderItemReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderItemReactiveObject : OrderItem, IUTSReactive<OrderItem> {
    override var __v_raw: OrderItem
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: OrderItem, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, quantity = __v_raw.quantity, price = __v_raw.price, product_name = __v_raw.product_name, images = __v_raw.images) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderItemReactiveObject {
        return OrderItemReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var quantity: Number
        get() {
            return _tRG(__v_raw, "quantity", __v_raw.quantity, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("quantity")) {
                return
            }
            val oldValue = __v_raw.quantity
            __v_raw.quantity = value
            _tRS(__v_raw, "quantity", oldValue, value)
        }
    override var price: Number
        get() {
            return _tRG(__v_raw, "price", __v_raw.price, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("price")) {
                return
            }
            val oldValue = __v_raw.price
            __v_raw.price = value
            _tRS(__v_raw, "price", oldValue, value)
        }
    override var product_name: String
        get() {
            return _tRG(__v_raw, "product_name", __v_raw.product_name, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("product_name")) {
                return
            }
            val oldValue = __v_raw.product_name
            __v_raw.product_name = value
            _tRS(__v_raw, "product_name", oldValue, value)
        }
    override var images: UTSArray<String>
        get() {
            return _tRG(__v_raw, "images", __v_raw.images, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("images")) {
                return
            }
            val oldValue = __v_raw.images
            __v_raw.images = value
            _tRS(__v_raw, "images", oldValue, value)
        }
}
open class Order (
    @JsonNotNull
    open var id: Number,
    @JsonNotNull
    open var order_number: String,
    @JsonNotNull
    open var status: String,
    @JsonNotNull
    open var total_amount: Number,
    @JsonNotNull
    open var items: UTSArray<OrderItem>,
) : UTSReactiveObject() {
    override fun __v_create(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): UTSReactiveObject {
        return OrderReactiveObject(this, __v_isReadonly, __v_isShallow, __v_skip)
    }
}
open class OrderReactiveObject : Order, IUTSReactive<Order> {
    override var __v_raw: Order
    override var __v_isReadonly: Boolean
    override var __v_isShallow: Boolean
    override var __v_skip: Boolean
    constructor(__v_raw: Order, __v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean) : super(id = __v_raw.id, order_number = __v_raw.order_number, status = __v_raw.status, total_amount = __v_raw.total_amount, items = __v_raw.items) {
        this.__v_raw = __v_raw
        this.__v_isReadonly = __v_isReadonly
        this.__v_isShallow = __v_isShallow
        this.__v_skip = __v_skip
    }
    override fun __v_clone(__v_isReadonly: Boolean, __v_isShallow: Boolean, __v_skip: Boolean): OrderReactiveObject {
        return OrderReactiveObject(this.__v_raw, __v_isReadonly, __v_isShallow, __v_skip)
    }
    override var id: Number
        get() {
            return _tRG(__v_raw, "id", __v_raw.id, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("id")) {
                return
            }
            val oldValue = __v_raw.id
            __v_raw.id = value
            _tRS(__v_raw, "id", oldValue, value)
        }
    override var order_number: String
        get() {
            return _tRG(__v_raw, "order_number", __v_raw.order_number, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("order_number")) {
                return
            }
            val oldValue = __v_raw.order_number
            __v_raw.order_number = value
            _tRS(__v_raw, "order_number", oldValue, value)
        }
    override var status: String
        get() {
            return _tRG(__v_raw, "status", __v_raw.status, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("status")) {
                return
            }
            val oldValue = __v_raw.status
            __v_raw.status = value
            _tRS(__v_raw, "status", oldValue, value)
        }
    override var total_amount: Number
        get() {
            return _tRG(__v_raw, "total_amount", __v_raw.total_amount, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("total_amount")) {
                return
            }
            val oldValue = __v_raw.total_amount
            __v_raw.total_amount = value
            _tRS(__v_raw, "total_amount", oldValue, value)
        }
    override var items: UTSArray<OrderItem>
        get() {
            return _tRG(__v_raw, "items", __v_raw.items, __v_isReadonly, __v_isShallow)
        }
        set(value) {
            if (!__v_canSet("items")) {
                return
            }
            val oldValue = __v_raw.items
            __v_raw.items = value
            _tRS(__v_raw, "items", oldValue, value)
        }
}
val GenPagesOrderListClass = CreateVueComponent(GenPagesOrderList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesOrderList.inheritAttrs, inject = GenPagesOrderList.inject, props = GenPagesOrderList.props, propsNeedCastKeys = GenPagesOrderList.propsNeedCastKeys, emits = GenPagesOrderList.emits, components = GenPagesOrderList.components, styles = GenPagesOrderList.styles)
}
, fun(instance, renderer): GenPagesOrderList {
    return GenPagesOrderList(instance, renderer)
}
)
val GenPagesAddressListClass = CreateVueComponent(GenPagesAddressList::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesAddressList.inheritAttrs, inject = GenPagesAddressList.inject, props = GenPagesAddressList.props, propsNeedCastKeys = GenPagesAddressList.propsNeedCastKeys, emits = GenPagesAddressList.emits, components = GenPagesAddressList.components, styles = GenPagesAddressList.styles)
}
, fun(instance, renderer): GenPagesAddressList {
    return GenPagesAddressList(instance, renderer)
}
)
val GenPagesAddressEditClass = CreateVueComponent(GenPagesAddressEdit::class.java, fun(): VueComponentOptions {
    return VueComponentOptions(type = "page", name = "", inheritAttrs = GenPagesAddressEdit.inheritAttrs, inject = GenPagesAddressEdit.inject, props = GenPagesAddressEdit.props, propsNeedCastKeys = GenPagesAddressEdit.propsNeedCastKeys, emits = GenPagesAddressEdit.emits, components = GenPagesAddressEdit.components, styles = GenPagesAddressEdit.styles)
}
, fun(instance, renderer): GenPagesAddressEdit {
    return GenPagesAddressEdit(instance, renderer)
}
)
fun createApp(): UTSJSONObject {
    val app = createSSRApp(GenAppClass)
    return _uO("app" to app)
}
fun main(app: IApp) {
    definePageRoutes()
    defineAppConfig()
    (createApp()["app"] as VueApp).mount(app, GenUniApp())
}
open class UniAppConfig : io.dcloud.uniapp.appframe.AppConfig {
    override var name: String = "1"
    override var appid: String = "__UNI__9239644"
    override var versionName: String = "1.0.0"
    override var versionCode: String = "100"
    override var uniCompilerVersion: String = "4.76"
    constructor() : super() {}
}
fun definePageRoutes() {
    __uniRoutes.push(UniPageRoute(path = "pages/index/index", component = GenPagesIndexIndexClass, meta = UniPageMeta(isQuit = true), style = _uM("navigationBarTitleText" to "首页")))
    __uniRoutes.push(UniPageRoute(path = "pages/cart/cart", component = GenPagesCartCartClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "购物车")))
    __uniRoutes.push(UniPageRoute(path = "pages/my/my", component = GenPagesMyMyClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "我的")))
    __uniRoutes.push(UniPageRoute(path = "pages/login/login", component = GenPagesLoginLoginClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "登录")))
    __uniRoutes.push(UniPageRoute(path = "pages/product/detail", component = GenPagesProductDetailClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "商品详情")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/create", component = GenPagesOrderCreateClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "确认订单")))
    __uniRoutes.push(UniPageRoute(path = "pages/order/list", component = GenPagesOrderListClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "我的订单")))
    __uniRoutes.push(UniPageRoute(path = "pages/address/list", component = GenPagesAddressListClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "收货地址")))
    __uniRoutes.push(UniPageRoute(path = "pages/address/edit", component = GenPagesAddressEditClass, meta = UniPageMeta(isQuit = false), style = _uM("navigationBarTitleText" to "编辑地址")))
}
val __uniTabBar: Map<String, Any?>? = _uM("color" to "#B8A9C9", "selectedColor" to "#FF69B4", "backgroundColor" to "#FFFFFF", "borderStyle" to "white", "list" to _uA(
    _uM("pagePath" to "pages/index/index", "text" to "首页", "iconPath" to "static/tabbar/home.png", "selectedIconPath" to "static/tabbar/home-active.png"),
    _uM("pagePath" to "pages/order/list", "text" to "订单", "iconPath" to "static/tabbar/order.png", "selectedIconPath" to "static/tabbar/order-active.png"),
    _uM("pagePath" to "pages/my/my", "text" to "我的", "iconPath" to "static/tabbar/my.png", "selectedIconPath" to "static/tabbar/my-active.png")
))
val __uniLaunchPage: Map<String, Any?> = _uM("url" to "pages/index/index", "style" to _uM("navigationBarTitleText" to "首页"))
fun defineAppConfig() {
    __uniConfig.entryPagePath = "/pages/index/index"
    __uniConfig.globalStyle = _uM("navigationBarTextStyle" to "white", "navigationBarTitleText" to "美食", "navigationBarBackgroundColor" to "#FF69B4", "backgroundColor" to "#FFF5F8")
    __uniConfig.getTabBarConfig = fun(): Map<String, Any>? {
        return _uM("color" to "#B8A9C9", "selectedColor" to "#FF69B4", "backgroundColor" to "#FFFFFF", "borderStyle" to "white", "list" to _uA(
            _uM("pagePath" to "pages/index/index", "text" to "首页", "iconPath" to "static/tabbar/home.png", "selectedIconPath" to "static/tabbar/home-active.png"),
            _uM("pagePath" to "pages/order/list", "text" to "订单", "iconPath" to "static/tabbar/order.png", "selectedIconPath" to "static/tabbar/order-active.png"),
            _uM("pagePath" to "pages/my/my", "text" to "我的", "iconPath" to "static/tabbar/my.png", "selectedIconPath" to "static/tabbar/my-active.png")
        ))
    }
    __uniConfig.tabBar = __uniConfig.getTabBarConfig()
    __uniConfig.conditionUrl = ""
    __uniConfig.uniIdRouter = _uM()
    __uniConfig.ready = true
}
open class GenUniApp : UniAppImpl() {
    open val vm: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
    open val `$vm`: GenApp?
        get() {
            return getAppVm() as GenApp?
        }
}
fun getApp(): GenUniApp {
    return getUniApp() as GenUniApp
}
