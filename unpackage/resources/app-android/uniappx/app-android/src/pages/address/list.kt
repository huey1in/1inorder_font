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
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
open class GenPagesAddressList : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            if (options.select === "true") {
                this.isSelectMode = true
            }
        }
        , __ins)
        onPageShow(fun() {
            this.fetchAddresses()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            if (_ctx.addresses.length > 0) {
                _cE("view", _uM("key" to 0, "class" to "address-list"), _uA(
                    _cE(Fragment, null, RenderHelpers.renderList(_ctx.addresses, fun(item, index, __index, _cached): Any {
                        return _cE("view", _uM("class" to "address-item", "key" to item.id, "onClick" to fun(){
                            _ctx.selectAddress(item)
                        }), _uA(
                            _cE("view", _uM("class" to "address-info"), _uA(
                                _cE("view", _uM("class" to "user-row"), _uA(
                                    _cE("text", _uM("class" to "name"), _tD(item.contact_name), 1),
                                    _cE("text", _uM("class" to "phone"), _tD(item.contact_phone), 1),
                                    if (isTrue(item.is_default)) {
                                        _cE("view", _uM("key" to 0, "class" to "tag"), _uA(
                                            _cE("text", _uM("class" to "tag-text"), "默认")
                                        ))
                                    } else {
                                        _cC("v-if", true)
                                    }
                                )),
                                _cE("text", _uM("class" to "address-detail"), _tD(item.address) + " " + _tD(item.detail || ""), 1)
                            )),
                            _cE("view", _uM("class" to "edit-btn", "onClick" to withModifiers(fun(){
                                _ctx.editAddress(item)
                            }, _uA(
                                "stop"
                            ))), _uA(
                                _cE("image", _uM("class" to "edit-icon", "src" to "/static/icons/icon-edit.svg", "mode" to "aspectFit"))
                            ), 8, _uA(
                                "onClick"
                            ))
                        ), 8, _uA(
                            "onClick"
                        ))
                    }), 128)
                ))
            } else {
                _cE("view", _uM("key" to 1, "class" to "empty-state"), _uA(
                    _cE("image", _uM("class" to "empty-icon", "src" to "/static/icons/icon-empty.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "empty-text"), "还没有收货地址哦 ♡")
                ))
            }
            ,
            _cE("view", _uM("class" to "footer-btn"), _uA(
                _cE("button", _uM("class" to "add-btn", "onClick" to _ctx.addAddress), _uA(
                    _cE("text", _uM("class" to "btn-text"), "+ 新增收货地址")
                ), 8, _uA(
                    "onClick"
                ))
            ))
        ))
    }
    open var addresses: UTSArray<Any> by `$data`
    open var isSelectMode: Boolean by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("addresses" to _uA<Any>(), "isSelectMode" to false)
    }
    open var fetchAddresses = ::gen_fetchAddresses_fn
    open fun gen_fetchAddresses_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            return
        }
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/addresses", method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success) {
                val list = res.data.data.addresses || _uA()
                this.addresses = list.map(fun(item: Any): Any {
                    return UTSJSONObject.assign(UTSJSONObject(), item, object : UTSJSONObject() {
                        var contact_name = item.name || item.contact_name
                        var contact_phone = item.phone || item.contact_phone
                        var address = item.detail || item.address
                    })
                }
                )
            }
        }
        ))
    }
    open var addAddress = ::gen_addAddress_fn
    open fun gen_addAddress_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/address/edit"))
    }
    open var editAddress = ::gen_editAddress_fn
    open fun gen_editAddress_fn(item: Any) {
        uni_navigateTo(NavigateToOptions(url = "/pages/address/edit?id=" + item.id))
    }
    open var selectAddress = ::gen_selectAddress_fn
    open fun gen_selectAddress_fn(item: Any) {
        if (this.isSelectMode) {
            val pages = getCurrentPages()
            val prevPage = pages[pages.length - 2]
            if (prevPage.`$vm`.setAddress) {
                prevPage.`$vm`.setAddress(item)
            }
            uni_navigateBack(null)
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingBottom" to "120rpx")), "address-list" to _pS(_uM("paddingTop" to "24rpx", "paddingRight" to "24rpx", "paddingBottom" to "24rpx", "paddingLeft" to "24rpx")), "address-item" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "marginBottom" to "24rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "boxShadow" to "0 4rpx 16rpx rgba(255, 105, 180, 0.1)", "borderTopWidth" to 1, "borderRightWidth" to 1, "borderBottomWidth" to 1, "borderLeftWidth" to 1, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFF0F5", "borderRightColor" to "#FFF0F5", "borderBottomColor" to "#FFF0F5", "borderLeftColor" to "#FFF0F5")), "address-info" to _pS(_uM("flex" to 1)), "user-row" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "marginBottom" to "16rpx")), "name" to _pS(_uM("fontSize" to "32rpx", "color" to "#5D4E6D", "marginRight" to "20rpx")), "phone" to _pS(_uM("fontSize" to "28rpx", "color" to "#888888", "marginRight" to "20rpx")), "tag" to _pS(_uM("backgroundColor" to "#FF69B4", "paddingTop" to "4rpx", "paddingRight" to "12rpx", "paddingBottom" to "4rpx", "paddingLeft" to "12rpx", "borderTopLeftRadius" to "10rpx", "borderTopRightRadius" to "10rpx", "borderBottomRightRadius" to "10rpx", "borderBottomLeftRadius" to "10rpx")), "tag-text" to _pS(_uM("fontSize" to "20rpx", "color" to "#FFFFFF")), "address-detail" to _pS(_uM("fontSize" to "28rpx", "color" to "#666666", "lineHeight" to 1.4)), "edit-btn" to _pS(_uM("paddingTop" to "20rpx", "paddingRight" to "20rpx", "paddingBottom" to "20rpx", "paddingLeft" to "20rpx")), "edit-icon" to _pS(_uM("width" to "40rpx", "height" to "40rpx")), "empty-state" to _pS(_uM("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "justifyContent" to "center", "paddingTop" to "200rpx")), "empty-icon" to _pS(_uM("width" to "200rpx", "height" to "200rpx", "marginBottom" to "30rpx", "opacity" to 0.5)), "empty-text" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "footer-btn" to _pS(_uM("position" to "fixed", "bottom" to 0, "left" to 0, "right" to 0, "paddingTop" to "20rpx", "paddingRight" to "30rpx", "paddingBottom" to calc(20, rpx + env(safe - area - inset - bottom)), "paddingLeft" to "30rpx", "backgroundColor" to "rgba(255,255,255,0.9)", "backdropFilter" to "blur(10px)")), "add-btn" to _pS(_uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "height" to "88rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "boxShadow" to "0 8rpx 20rpx rgba(255, 105, 180, 0.3)", "transform:active" to "scale(0.98)")), "btn-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to "32rpx", "fontWeight" to "bold", "letterSpacing" to 1)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
