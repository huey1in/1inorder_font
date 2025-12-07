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
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesIndexIndex : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.fetchShopInfo()
            this.fetchCategories()
            this.fetchCartCount()
        }
        , __ins)
        onPageShow(fun() {
            this.fetchCartCount()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "shop-header"), _uA(
                _cE("view", _uM("class" to "header-bg-circle circle-1")),
                _cE("view", _uM("class" to "header-bg-circle circle-2")),
                _cE("view", _uM("class" to "shop-content"), _uA(
                    _cE("view", _uM("class" to "shop-info"), _uA(
                        _cE("text", _uM("class" to "shop-name"), _tD(_ctx.shop.name), 1),
                        _cE("text", _uM("class" to "shop-desc"), _tD(_ctx.shop.description), 1),
                        _cE("view", _uM("class" to "shop-status-row"), _uA(
                            _cE("view", _uM("class" to _nC(_uA(
                                "status-badge",
                                if (_ctx.shop.is_open) {
                                    "open"
                                } else {
                                    "closed"
                                }
                            ))), _uA(
                                _cE("view", _uM("class" to "status-dot")),
                                _cE("text", _uM("class" to "status-text"), _tD(if (_ctx.shop.is_open) {
                                    "营业中"
                                } else {
                                    "休息中"
                                }
                                ), 1)
                            ), 2),
                            _cE("text", _uM("class" to "opening-hours"), _tD(_ctx.shop.opening_hours), 1)
                        ))
                    ))
                )),
                _cE("view", _uM("class" to "shop-meta"), _uA(
                    if (isTrue(_ctx.shop.address)) {
                        _cE("view", _uM("key" to 0, "class" to "meta-row"), _uA(
                            _cE("image", _uM("class" to "meta-icon", "src" to "/static/icons/icon-address.svg", "mode" to "aspectFit")),
                            _cE("text", _uM("class" to "meta-text"), _tD(_ctx.shop.address), 1)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(_ctx.shop.phone)) {
                        _cE("view", _uM("key" to 1, "class" to "meta-row"), _uA(
                            _cE("image", _uM("class" to "meta-icon", "src" to "/static/icons/icon-phone.svg", "mode" to "aspectFit")),
                            _cE("text", _uM("class" to "meta-text"), _tD(_ctx.shop.phone), 1)
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    _cE("view", _uM("class" to "meta-tags"), _uA(
                        _cE("text", _uM("class" to "meta-tag"), "起送￥" + _tD(_ctx.shop.delivery_start_amount || 0), 1),
                        _cE("text", _uM("class" to "meta-tag"), "配送费￥" + _tD(_ctx.shop.delivery_fee || 0), 1),
                        _cE("text", _uM("class" to "meta-tag"), "起订￥" + _tD(_ctx.shop.min_order_amount || 0), 1)
                    ))
                ))
            )),
            if (isTrue(_ctx.shop.announcement)) {
                _cE("view", _uM("key" to 0, "class" to "announcement"), _uA(
                    _cE("image", _uM("class" to "announcement-icon", "src" to "/static/icons/icon-notice.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "announcement-text"), _tD(_ctx.shop.announcement), 1)
                ))
            } else {
                _cC("v-if", true)
            }
            ,
            _cE("view", _uM("class" to "main-content"), _uA(
                _cE("scroll-view", _uM("class" to "category-list", "scroll-y" to ""), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.categories, fun(item, index, __index, _cached): Any {
                        return _cE("view", _uM("key" to item.id, "class" to _nC(_uA(
                            "category-item",
                            if (_ctx.currentCategory === index) {
                                "active"
                            } else {
                                ""
                            }
                        )), "onClick" to fun(){
                            _ctx.selectCategory(index)
                        }
                        ), _uA(
                            _cE("text", _uM("class" to "category-name"), _tD(item.name), 1)
                        ), 10, _uA(
                            "onClick"
                        ))
                    }
                    ), 128)
                )),
                _cE("scroll-view", _uM("class" to "product-list", "scroll-y" to "", "onScrolltolower" to _ctx.loadMore), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.products, fun(product, __key, __index, _cached): Any {
                        return _cE("view", _uM("class" to "product-item", "key" to product.id, "onClick" to fun(){
                            _ctx.goToDetail(product.id)
                        }
                        ), _uA(
                            _cE("image", _uM("class" to "product-image", "src" to _ctx.getProductImage(product), "mode" to "aspectFill", "onError" to fun(){
                                _ctx.handleImageError(product)
                            }
                            ), null, 40, _uA(
                                "src",
                                "onError"
                            )),
                            _cE("view", _uM("class" to "product-info"), _uA(
                                _cE("text", _uM("class" to "product-name"), _tD(product.name), 1),
                                _cE("text", _uM("class" to "product-desc"), _tD(product.description), 1),
                                _cE("view", _uM("class" to "product-bottom"), _uA(
                                    _cE("view", _uM("class" to "price-box"), _uA(
                                        _cE("text", _uM("class" to "price"), "¥" + _tD(product.price), 1),
                                        if (isTrue(product.original_price)) {
                                            _cE("text", _uM("key" to 0, "class" to "original-price"), "¥" + _tD(product.original_price), 1)
                                        } else {
                                            _cC("v-if", true)
                                        }
                                    )),
                                    _cE("view", _uM("class" to "cart-btn", "onClick" to withModifiers(fun(){
                                        _ctx.addToCart(product)
                                    }
                                    , _uA(
                                        "stop"
                                    ))), _uA(
                                        _cE("text", _uM("class" to "cart-btn-text"), "+")
                                    ), 8, _uA(
                                        "onClick"
                                    ))
                                ))
                            ))
                        ), 8, _uA(
                            "onClick"
                        ))
                    }
                    ), 128),
                    if (isTrue(_ctx.loading)) {
                        _cE("view", _uM("key" to 0, "class" to "loading-more"), _uA(
                            _cE("text", null, "加载中...")
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                    ,
                    if (isTrue(!_ctx.loading && _ctx.noMore)) {
                        _cE("view", _uM("key" to 1, "class" to "no-more"), _uA(
                            _cE("text", null, "没有更多了")
                        ))
                    } else {
                        _cC("v-if", true)
                    }
                ), 40, _uA(
                    "onScrolltolower"
                ))
            )),
            if (isTrue(_ctx.showCartPopup)) {
                _cE("view", _uM("key" to 1, "class" to "cart-overlay", "onClick" to _ctx.toggleCartPopup), null, 8, _uA(
                    "onClick"
                ))
            } else {
                _cC("v-if", true)
            }
            ,
            if (_ctx.cartCount > 0) {
                _cE("view", _uM("key" to 2, "class" to "cart-container"), _uA(
                    _cE("view", _uM("class" to _nC(_uA(
                        "cart-expand-list",
                        if (_ctx.showCartPopup) {
                            "show"
                        } else {
                            ""
                        }
                    ))), _uA(
                        _cE("view", _uM("class" to "cart-expand-header"), _uA(
                            _cE("text", _uM("class" to "cart-expand-title"), "♡ 已选商品 ♡"),
                            _cE("view", _uM("class" to "cart-clear-btn", "onClick" to _ctx.clearCart), _uA(
                                _cE("text", _uM("class" to "cart-clear-text"), "清空")
                            ), 8, _uA(
                                "onClick"
                            ))
                        )),
                        _cE("scroll-view", _uM("class" to "cart-expand-scroll", "scroll-y" to ""), _uA(
                            _cE(Fragment, null, RenderHelpers.renderList(_ctx.cartItems, fun(item, __key, __index, _cached): Any {
                                return _cE("view", _uM("class" to "cart-expand-item", "key" to item.id), _uA(
                                    _cE("image", _uM("class" to "cart-expand-img", "src" to _ctx.getCartItemImage(item), "mode" to "aspectFill", "onError" to fun(){
                                        _ctx.handleImageError(item)
                                    }), null, 40, _uA(
                                        "src",
                                        "onError"
                                    )),
                                    _cE("view", _uM("class" to "cart-expand-info"), _uA(
                                        _cE("text", _uM("class" to "cart-expand-name"), _tD(item.name), 1),
                                        _cE("text", _uM("class" to "cart-expand-price"), "¥" + _tD(item.price), 1)
                                    )),
                                    _cE("view", _uM("class" to "cart-expand-qty"), _uA(
                                        _cE("view", _uM("class" to "cart-qty-btn", "onClick" to fun(){
                                            _ctx.changeQuantity(item, -1)
                                        }), _uA(
                                            _cE("text", _uM("class" to "cart-qty-text"), "-")
                                        ), 8, _uA(
                                            "onClick"
                                        )),
                                        _cE("text", _uM("class" to "cart-qty-num"), _tD(item.quantity), 1),
                                        _cE("view", _uM("class" to "cart-qty-btn", "onClick" to fun(){
                                            _ctx.changeQuantity(item, 1)
                                        }), _uA(
                                            _cE("text", _uM("class" to "cart-qty-text"), "+")
                                        ), 8, _uA(
                                            "onClick"
                                        ))
                                    ))
                                ))
                            }), 128)
                        ))
                    ), 2),
                    _cE("view", _uM("class" to _nC(_uA(
                        "cart-footer",
                        if (_ctx.showCartPopup) {
                            "expanded"
                        } else {
                            ""
                        }
                    ))), _uA(
                        _cE("view", _uM("class" to "cart-footer-left", "onClick" to _ctx.toggleCartPopup), _uA(
                            _cE("view", _uM("class" to "cart-icon-box"), _uA(
                                _cE("image", _uM("class" to "cart-footer-icon", "src" to "/static/icons/icon-cart.svg", "mode" to "aspectFit")),
                                _cE("view", _uM("class" to "cart-count-badge"), _uA(
                                    _cE("text", _uM("class" to "cart-count-text"), _tD(_ctx.cartCount), 1)
                                ))
                            )),
                            _cE("text", _uM("class" to "cart-footer-total"), "¥" + _tD(_ctx.cartTotal.toFixed(2)), 1)
                        ), 8, _uA(
                            "onClick"
                        )),
                        _cE("view", _uM("class" to "cart-footer-btn", "onClick" to _ctx.goToCheckout), _uA(
                            _cE("text", _uM("class" to "cart-footer-btn-text"), "去结算 ♡")
                        ), 8, _uA(
                            "onClick"
                        ))
                    ), 2)
                ))
            } else {
                _cC("v-if", true)
            }
        ))
    }
    open var shop: UTSJSONObject by `$data`
    open var categories: UTSArray<{
        var id: Number
        var name: String
    }> by `$data`
    open var products: UTSArray<{
        var id: Number
        var name: String
        var description: String
        var price: Number
        var original_price: Number
        var images: UTSArray<String>
    }> by `$data`
    open var currentCategory: Number by `$data`
    open var cartCount: Number by `$data`
    open var cartTotal: Number by `$data`
    open var cartItems: UTSArray<{
        var id: Number
        var product_id: Number
        var name: String
        var price: Number
        var quantity: Number
        var images: UTSArray<String>
    }> by `$data`
    open var showCartPopup: Boolean by `$data`
    open var loading: Boolean by `$data`
    open var noMore: Boolean by `$data`
    open var page: Number by `$data`
    open var limit: Number by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("shop" to object : UTSJSONObject() {
            var name = ""
            var logo = ""
            var description = ""
            var opening_hours = ""
            var is_open = false
            var announcement = ""
            var address = ""
            var phone = ""
            var min_order_amount: Number = 0
            var delivery_fee: Number = 0
            var delivery_start_amount: Number = 0
        }, "categories" to _uA<{
            var id: Number
            var name: String
        }>(), "products" to _uA<{
            var id: Number
            var name: String
            var description: String
            var price: Number
            var original_price: Number
            var images: UTSArray<String>
        }>(), "currentCategory" to 0, "cartCount" to 0, "cartTotal" to 0, "cartItems" to _uA<{
            var id: Number
            var product_id: Number
            var name: String
            var price: Number
            var quantity: Number
            var images: UTSArray<String>
        }>(), "showCartPopup" to false, "loading" to false, "noMore" to false, "page" to 1, "limit" to 10)
    }
    open var fetchShopInfo = ::gen_fetchShopInfo_fn
    open fun gen_fetchShopInfo_fn() {
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/shop/info", method = "GET", success = fun(res: Any){
            if (res.data.success && res.data.data.shop) {
                val info = res.data.data.shop
                this.shop = UTSJSONObject.assign(UTSJSONObject(), this.shop, info, object : UTSJSONObject() {
                    var is_open = if ((if (info.is_currently_open !== undefined) {
                        info.is_currently_open
                    } else {
                        info.is_open
                    }
                    )) {
                        true
                    } else {
                        false
                    }
                })
            }
        }
        , fail = fun(err){
            console.log("获取店铺信息失败", err)
        }
        ))
    }
    open var fetchCategories = ::gen_fetchCategories_fn
    open fun gen_fetchCategories_fn() {
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/categories", method = "GET", success = fun(res: Any){
            if (res.data.success && res.data.data.categories) {
                this.categories = res.data.data.categories
                if (this.categories.length > 0) {
                    this.fetchProducts()
                }
            }
        }
        , fail = fun(err){
            console.log("获取分类失败", err)
        }
        ))
    }
    open var fetchProducts = ::gen_fetchProducts_fn
    open fun gen_fetchProducts_fn() {
        if (this.loading) {
            return
        }
        this.loading = true
        val categoryId = this.categories[this.currentCategory]?.id
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/products", method = "GET", data = _uO("category_id" to categoryId, "page" to this.page, "limit" to this.limit), success = fun(res: Any){
            if (res.data.success && res.data.data.products) {
                val newProducts = res.data.data.products
                if (this.page === 1) {
                    this.products = newProducts
                } else {
                    this.products = this.products.concat(newProducts)
                }
                this.noMore = newProducts.length < this.limit
            }
        }
        , fail = fun(err){
            console.log("获取商品失败", err)
        }
        , complete = fun(_){
            this.loading = false
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
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success && res.data.data.cart) {
                this.cartTotal = res.data.data.cart.total_amount || 0
                val items = res.data.data.cart.items || _uA()
                this.cartItems = items.map(fun(item: Any): UTSJSONObject {
                    return (object : UTSJSONObject() {
                        var id = item.cart_item_id
                        var product_id = item.product_id
                        var name = item.name
                        var price = item.price
                        var quantity = item.quantity
                        var images = item.images || _uA()
                    })
                }
                )
            }
        }
        ))
    }
    open var selectCategory = ::gen_selectCategory_fn
    open fun gen_selectCategory_fn(index: Number) {
        this.currentCategory = index
        this.page = 1
        this.noMore = false
        this.products = _uA()
        this.fetchProducts()
    }
    open var loadMore = ::gen_loadMore_fn
    open fun gen_loadMore_fn() {
        if (this.noMore || this.loading) {
            return
        }
        this.page++
        this.fetchProducts()
    }
    open var addToCart = ::gen_addToCart_fn
    open fun gen_addToCart_fn(product: Any) {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/add", method = "POST", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = object : UTSJSONObject() {
            var product_id = product.id
            var quantity: Number = 1
        }, success = fun(res: Any){
            if (res.data.success) {
                uni_showToast(ShowToastOptions(title = "已加入购物车", icon = "success"))
                this.fetchCartCount()
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "添加失败", icon = "none"))
            }
        }
        , fail = fun(_){
            uni_showToast(ShowToastOptions(title = "网络错误", icon = "none"))
        }
        ))
    }
    open var goToDetail = ::gen_goToDetail_fn
    open fun gen_goToDetail_fn(id: Number) {
        uni_navigateTo(NavigateToOptions(url = "/pages/product/detail?id=" + id))
    }
    open var getProductImage = ::gen_getProductImage_fn
    open fun gen_getProductImage_fn(product: Any): String {
        if (product.images && product.images.length > 0) {
            return product.images[0]
        }
        return DEFAULT_FOOD_IMAGE
    }
    open var getCartItemImage = ::gen_getCartItemImage_fn
    open fun gen_getCartItemImage_fn(item: Any): String {
        if (item.images && item.images.length > 0) {
            return item.images[0]
        }
        return DEFAULT_FOOD_IMAGE
    }
    open var toggleCartPopup = ::gen_toggleCartPopup_fn
    open fun gen_toggleCartPopup_fn() {
        this.showCartPopup = !this.showCartPopup
        if (this.showCartPopup) {
            this.fetchCartCount()
        }
    }
    open var changeQuantity = ::gen_changeQuantity_fn
    open fun gen_changeQuantity_fn(item: Any, delta: Number) {
        val newQty = item.quantity + delta
        if (newQty < 1) {
            this.removeCartItem(item)
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
                this.fetchCartCount()
            }
        }
        ))
    }
    open var removeCartItem = ::gen_removeCartItem_fn
    open fun gen_removeCartItem_fn(item: Any) {
        val token = uni_getStorageSync("token")
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/item/" + item.id, method = "DELETE", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, success = fun(res: Any){
            if (res.data.success) {
                val index = this.cartItems.findIndex(fun(i: Any): Boolean {
                    return i.id === item.id
                }
                )
                if (index > -1) {
                    this.cartItems.splice(index, 1)
                }
                this.fetchCartCount()
                if (this.cartItems.length === 0) {
                    this.showCartPopup = false
                }
            }
        }
        ))
    }
    open var clearCart = ::gen_clearCart_fn
    open fun gen_clearCart_fn() {
        uni_showModal(ShowModalOptions(title = "确认清空", content = "确定要清空购物车吗？", success = fun(res){
            if (res.confirm) {
                val token = uni_getStorageSync("token")
                uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/cart/clear", method = "DELETE", header = object : UTSJSONObject() {
                    var Authorization = "Bearer " + token
                }, success = fun(res: Any){
                    if (res.data.success) {
                        this.cartItems = _uA()
                        this.cartCount = 0
                        this.cartTotal = 0
                        this.showCartPopup = false
                    }
                }
                ))
            }
        }
        ))
    }
    open var goToCheckout = ::gen_goToCheckout_fn
    open fun gen_goToCheckout_fn() {
        this.showCartPopup = false
        uni_navigateTo(NavigateToOptions(url = "/pages/order/create"))
    }
    open var handleImageError = ::gen_handleImageError_fn
    open fun gen_handleImageError_fn(item: Any) {
        if (item.images) {
            item.images = _uA(
                DEFAULT_FOOD_IMAGE
            )
        }
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
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundColor" to "#FFF5F8")), "shop-header" to _pS(_uM("position" to "relative", "paddingTop" to "40rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "backgroundImage" to "linear-gradient(135deg, #FF9A9E 0%, #FECFEF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "0 10rpx 30rpx rgba(255, 105, 180, 0.2)", "overflow" to "hidden")), "header-bg-circle" to _pS(_uM("position" to "absolute", "backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.15)", "pointerEvents" to "none")), "circle-1" to _pS(_uM("width" to "300rpx", "height" to "300rpx", "top" to "-100rpx", "right" to "-50rpx")), "circle-2" to _pS(_uM("width" to "200rpx", "height" to "200rpx", "bottom" to "-50rpx", "left" to "-50rpx")), "shop-content" to _pS(_uM("position" to "relative", "zIndex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "shop-info" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "column")), "shop-name" to _pS(_uM("fontSize" to "44rpx", "color" to "#FFFFFF", "letterSpacing" to 2, "textShadow" to "0 2rpx 4rpx rgba(0,0,0,0.1)", "marginBottom" to "8rpx")), "shop-desc" to _pS(_uM("fontSize" to "26rpx", "color" to "rgba(255,255,255,0.95)", "marginBottom" to "16rpx")), "shop-status-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center")), "status-badge" to _uM("" to _uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "6rpx", "paddingRight" to "16rpx", "paddingBottom" to "6rpx", "paddingLeft" to "16rpx", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "backdropFilter" to "blur(5px)", "marginRight" to "16rpx"), ".open" to _uM("backgroundImage" to "none", "backgroundColor" to "rgba(152,216,170,0.9)"), ".closed" to _uM("backgroundImage" to "none", "backgroundColor" to "rgba(255,107,107,0.9)")), "status-dot" to _pS(_uM("width" to "12rpx", "height" to "12rpx", "backgroundColor" to "#ffffff", "marginRight" to "8rpx")), "status-text" to _pS(_uM("fontSize" to "22rpx", "color" to "#FFFFFF", "fontWeight" to "700")), "opening-hours" to _pS(_uM("fontSize" to "24rpx", "color" to "rgba(255,255,255,0.9)")), "shop-meta" to _pS(_uM("position" to "relative", "zIndex" to 1, "marginTop" to "20rpx", "backgroundImage" to "none", "backgroundColor" to "rgba(255,255,255,0.18)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.3)", "borderRightColor" to "rgba(255,255,255,0.3)", "borderBottomColor" to "rgba(255,255,255,0.3)", "borderLeftColor" to "rgba(255,255,255,0.3)", "borderTopLeftRadius" to "24rpx", "borderTopRightRadius" to "24rpx", "borderBottomRightRadius" to "24rpx", "borderBottomLeftRadius" to "24rpx", "paddingTop" to "16rpx", "paddingRight" to "20rpx", "paddingBottom" to "16rpx", "paddingLeft" to "20rpx", "backdropFilter" to "blur(8px)")), "meta-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "10rpx")), "meta-icon" to _pS(_uM("width" to "28rpx", "height" to "28rpx", "marginRight" to "12rpx")), "meta-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#FFFFFF", "opacity" to 0.95)), "meta-tags" to _pS(_uM("display" to "flex", "flexDirection" to "row", "flexWrap" to "wrap", "gap" to "10rpx", "marginTop" to "8rpx")), "meta-tag" to _pS(_uM("fontSize" to "24rpx", "color" to "#FF69B4", "backgroundImage" to "none", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "paddingTop" to "6rpx", "paddingRight" to "14rpx", "paddingBottom" to "6rpx", "paddingLeft" to "14rpx", "fontWeight" to "700", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,105,180,0.2)", "borderRightColor" to "rgba(255,105,180,0.2)", "borderBottomColor" to "rgba(255,105,180,0.2)", "borderLeftColor" to "rgba(255,105,180,0.2)")), "announcement" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "20rpx", "paddingLeft" to "30rpx", "marginTop" to "24rpx", "marginRight" to "24rpx", "marginBottom" to "24rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.8)", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "dashed", "borderRightStyle" to "dashed", "borderBottomStyle" to "dashed", "borderLeftStyle" to "dashed", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "boxShadow" to "0 4rpx 16rpx rgba(255, 105, 180, 0.05)", "backdropFilter" to "blur(10px)")), "announcement-icon" to _pS(_uM("width" to "44rpx", "height" to "44rpx", "marginRight" to "16rpx")), "announcement-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#5D4E6D", "flex" to 1)), "main-content" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "row", "overflow" to "hidden", "marginTop" to 0, "marginRight" to "24rpx", "marginBottom" to 0, "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 8rpx 30rpx rgba(255, 105, 180, 0.15)", "backdropFilter" to "blur(20px)")), "category-list" to _pS(_uM("width" to "190rpx", "backgroundColor" to "#FFF0F5", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to 0, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to "40rpx")), "category-item" to _uM("" to _uM("paddingTop" to "36rpx", "paddingRight" to "20rpx", "paddingBottom" to "36rpx", "paddingLeft" to "20rpx", "textAlign" to "center", "position" to "relative", "transitionProperty" to "all", "transitionDuration" to "0.3s", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)"), ".active" to _uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to 0, "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to "30rpx"), ".active::before" to _uM("content" to "''", "position" to "absolute", "left" to 0, "top" to "50%", "transform" to "translateY(-50%)", "width" to "8rpx", "height" to "40rpx", "backgroundColor" to "#FF69B4", "borderTopLeftRadius" to 0, "borderTopRightRadius" to "8rpx", "borderBottomRightRadius" to "8rpx", "borderBottomLeftRadius" to 0)), "category-name" to _uM("" to _uM("fontSize" to "28rpx", "color" to "#B8A9C9", "transitionProperty" to "all", "transitionDuration" to "0.3s"), ".category-item.active " to _uM("color" to "#FF69B4", "fontSize" to "30rpx", "transform" to "scale(1.05)")), "product-list" to _pS(_uM("flex" to 1, "backgroundColor" to "#FFFFFF", "paddingTop" to 0, "paddingRight" to "24rpx", "paddingBottom" to 0, "paddingLeft" to "24rpx", "borderTopLeftRadius" to 0, "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to 0)), "product-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "paddingTop" to "30rpx", "paddingRight" to 0, "paddingBottom" to "30rpx", "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "product-image" to _pS(_uM("width" to "180rpx", "height" to "180rpx", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "borderTopWidth" to "4rpx", "borderRightWidth" to "4rpx", "borderBottomWidth" to "4rpx", "borderLeftWidth" to "4rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5", "boxShadow" to "0 4rpx 12rpx rgba(0,0,0,0.05)")), "product-info" to _pS(_uM("flex" to 1, "marginLeft" to "24rpx", "display" to "flex", "flexDirection" to "column", "justifyContent" to "space-between", "paddingTop" to "4rpx", "paddingRight" to 0, "paddingBottom" to "4rpx", "paddingLeft" to 0)), "product-name" to _pS(_uM("fontSize" to "32rpx", "fontWeight" to "700", "color" to "#5D4E6D")), "product-desc" to _pS(_uM("fontSize" to "24rpx", "color" to "#B8A9C9", "marginTop" to "10rpx", "lineHeight" to 1.4, "overflow" to "hidden", "textOverflow" to "ellipsis", "WebkitLineClamp" to 2, "WebkitBoxOrient" to "vertical")), "product-bottom" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "marginTop" to "16rpx")), "price-box" to _pS(_uM("display" to "flex", "flexDirection" to "row")), "price" to _pS(_uM("fontSize" to "36rpx", "color" to "#FF69B4")), "original-price" to _pS(_uM("fontSize" to "24rpx", "color" to "#D4C4E3", "textDecoration" to "line-through", "marginLeft" to "12rpx")), "cart-btn" to _pS(_uM("width" to "64rpx", "height" to "64rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 6rpx 16rpx rgba(255, 105, 180, 0.3)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.9)")), "cart-btn-text" to _pS(_uM("fontSize" to "40rpx", "color" to "#ffffff", "fontWeight" to "bold", "lineHeight" to 1, "marginTop" to "-4rpx")), "loading-more" to _pS(_uM("paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "textAlign" to "center", "color" to "#B8A9C9", "fontSize" to "26rpx")), "no-more" to _pS(_uM("paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "textAlign" to "center", "color" to "#B8A9C9", "fontSize" to "26rpx")), "cart-overlay" to _pS(_uM("position" to "fixed", "top" to 0, "left" to 0, "right" to 0, "bottom" to 0, "backgroundColor" to "rgba(0,0,0,0.4)", "zIndex" to 998)), "cart-container" to _pS(_uM("position" to "fixed", "left" to "24rpx", "right" to "24rpx", "bottom" to "20rpx", "zIndex" to 999, "display" to "flex", "flexDirection" to "column")), "cart-expand-list" to _uM("" to _uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "maxHeight" to 0, "opacity" to 0, "transform" to "translateY(20rpx)", "display" to "flex", "flexDirection" to "column", "boxShadow" to "0 -5rpx 20rpx rgba(255, 105, 180, 0.1)", "overflow" to "hidden", "transitionProperty" to "all", "transitionDuration" to "0.3s", "transitionTimingFunction" to "cubic-bezier(0.4,0,0.2,1)"), ".show" to _uM("opacity" to 1, "transform" to "translateY(0)")), "cart-expand-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "30rpx", "paddingRight" to "36rpx", "paddingBottom" to "30rpx", "paddingLeft" to "36rpx", "backgroundColor" to "#FFF5F8", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to 0, "borderBottomLeftRadius" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#FFE4EC")), "cart-expand-title" to _pS(_uM("fontSize" to "32rpx", "color" to "#FF69B4")), "cart-clear-btn" to _pS(_uM("paddingTop" to "12rpx", "paddingRight" to "24rpx", "paddingBottom" to "12rpx", "paddingLeft" to "24rpx", "backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx")), "cart-clear-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9")), "cart-expand-scroll" to _pS(_uM("paddingTop" to 0, "paddingRight" to "36rpx", "paddingBottom" to 0, "paddingLeft" to "36rpx", "backgroundColor" to "#FFFFFF")), "cart-expand-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "24rpx", "paddingRight" to 0, "paddingBottom" to "24rpx", "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#FFE4EC")), "cart-expand-img" to _pS(_uM("width" to "100rpx", "height" to "100rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "borderTopWidth" to "3rpx", "borderRightWidth" to "3rpx", "borderBottomWidth" to "3rpx", "borderLeftWidth" to "3rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5")), "cart-expand-info" to _pS(_uM("flex" to 1, "marginLeft" to "20rpx", "display" to "flex", "flexDirection" to "column")), "cart-expand-name" to _pS(_uM("fontSize" to "28rpx", "color" to "#5D4E6D", "marginBottom" to "8rpx")), "cart-expand-price" to _pS(_uM("fontSize" to "30rpx", "color" to "#FF69B4")), "cart-expand-qty" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "backgroundColor" to "#FFF0F5", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "paddingTop" to "6rpx", "paddingRight" to "6rpx", "paddingBottom" to "6rpx", "paddingLeft" to "6rpx")), "cart-qty-btn" to _pS(_uM("width" to "52rpx", "height" to "52rpx", "backgroundColor" to "#FFFFFF", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "backgroundColor:active" to "#FFE4EC")), "cart-qty-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FF69B4", "fontWeight" to "bold")), "cart-qty-num" to _pS(_uM("fontSize" to "28rpx", "color" to "#5D4E6D", "paddingTop" to 0, "paddingRight" to "20rpx", "paddingBottom" to 0, "paddingLeft" to "20rpx", "fontWeight" to "bold")), "cart-footer" to _uM("" to _uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to "20rpx", "paddingLeft" to "30rpx", "backgroundColor" to "rgba(255,255,255,0.98)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 4rpx 16rpx rgba(255, 105, 180, 0.15)", "transitionProperty" to "all", "transitionDuration" to "0.3s", "position" to "relative", "zIndex" to 10, "overflow" to "visible"), ".expanded" to _uM("borderTopLeftRadius" to 0, "borderTopRightRadius" to 0, "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 4rpx 16rpx rgba(255, 105, 180, 0.15)")), "cart-footer-left" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "overflow" to "visible")), "cart-icon-box" to _pS(_uM("position" to "relative", "width" to "90rpx", "height" to "90rpx", "backgroundColor" to "#FFF0F5", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "overflow" to "visible")), "cart-footer-icon" to _pS(_uM("width" to "48rpx", "height" to "48rpx")), "cart-count-badge" to _pS(_uM("position" to "absolute", "top" to "-8rpx", "right" to "-8rpx", "minWidth" to "40rpx", "height" to "40rpx", "backgroundImage" to "linear-gradient(135deg, #FF69B4 0%, #FF1493 100%)", "backgroundColor" to "rgba(0,0,0,0)", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "paddingTop" to 0, "paddingRight" to "10rpx", "paddingBottom" to 0, "paddingLeft" to "10rpx", "borderTopLeftRadius" to "20rpx", "borderTopRightRadius" to "20rpx", "borderBottomRightRadius" to "20rpx", "borderBottomLeftRadius" to "20rpx", "borderTopWidth" to "3rpx", "borderRightWidth" to "3rpx", "borderBottomWidth" to "3rpx", "borderLeftWidth" to "3rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFFFFF", "borderRightColor" to "#FFFFFF", "borderBottomColor" to "#FFFFFF", "borderLeftColor" to "#FFFFFF", "zIndex" to 100, "boxShadow" to "0 2rpx 8rpx rgba(255, 20, 147, 0.3)")), "cart-count-text" to _pS(_uM("fontSize" to "24rpx", "color" to "#FFFFFF", "fontWeight" to "bold")), "cart-footer-total" to _pS(_uM("fontSize" to "40rpx", "color" to "#5D4E6D", "marginLeft" to "20rpx")), "cart-footer-btn" to _pS(_uM("paddingTop" to "24rpx", "paddingRight" to "50rpx", "paddingBottom" to "24rpx", "paddingLeft" to "50rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.4)", "opacity:active" to 0.9)), "cart-footer-btn-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FFFFFF", "fontWeight" to "bold", "letterSpacing" to 1)), "@TRANSITION" to _uM("category-item" to _uM("property" to "all", "duration" to "0.3s", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)"), "category-name" to _uM("property" to "all", "duration" to "0.3s"), "cart-btn" to _uM("property" to "transform", "duration" to "0.2s"), "cart-expand-list" to _uM("property" to "all", "duration" to "0.3s", "timingFunction" to "cubic-bezier(0.4,0,0.2,1)"), "cart-footer" to _uM("property" to "all", "duration" to "0.3s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
