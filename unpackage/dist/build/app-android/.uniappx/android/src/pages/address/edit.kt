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
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setNavigationBarTitle as uni_setNavigationBarTitle
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesAddressEdit : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(options: Any) {
            if (options.id) {
                this.isEdit = true
                this.id = options.id
                this.fetchAddressDetail(options.id)
                uni_setNavigationBarTitle(SetNavigationBarTitleOptions(title = "♡ 编辑地址 ♡"))
            } else {
                uni_setNavigationBarTitle(SetNavigationBarTitleOptions(title = "♡ 新增地址 ♡"))
            }
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        val _component_switch = resolveComponent("switch")
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "form-card"), _uA(
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "联系人"),
                    _cE("input", _uM("class" to "input", "type" to "text", "modelValue" to _ctx.form.contact_name, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.contact_name = `$event`.detail.value
                    }
                    , "placeholder" to "请填写收货人姓名", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "手机号"),
                    _cE("input", _uM("class" to "input", "type" to "number", "modelValue" to _ctx.form.contact_phone, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.contact_phone = `$event`.detail.value
                    }
                    , "placeholder" to "请填写收货人手机号", "placeholder-class" to "placeholder", "maxlength" to "11"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "收货地址"),
                    _cE("input", _uM("class" to "input", "type" to "text", "modelValue" to _ctx.form.address, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.address = `$event`.detail.value
                    }
                    , "placeholder" to "小区/写字楼/学校等", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "门牌号"),
                    _cE("input", _uM("class" to "input", "type" to "text", "modelValue" to _ctx.form.detail, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.detail = `$event`.detail.value
                    }
                    , "placeholder" to "例：8号楼808室", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item switch-item"), _uA(
                    _cE("text", _uM("class" to "label"), "设为默认地址"),
                    _cV(_component_switch, _uM("checked" to _ctx.form.is_default, "color" to "#FF69B4", "onChange" to _ctx.onSwitchChange), null, 8, _uA(
                        "checked",
                        "onChange"
                    ))
                ))
            )),
            _cE("view", _uM("class" to "footer-btns"), _uA(
                _cE("button", _uM("class" to "save-btn", "onClick" to _ctx.saveAddress), _uA(
                    _cE("text", _uM("class" to "save-text"), "保存地址")
                ), 8, _uA(
                    "onClick"
                )),
                if (isTrue(_ctx.isEdit)) {
                    _cE("button", _uM("key" to 0, "class" to "delete-btn", "onClick" to _ctx.deleteAddress), _uA(
                        _cE("text", _uM("class" to "delete-text"), "删除地址")
                    ), 8, _uA(
                        "onClick"
                    ))
                } else {
                    _cC("v-if", true)
                }
            ))
        ))
    }
    open var isEdit: Boolean by `$data`
    open var id: String by `$data`
    open var form: UTSJSONObject by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("isEdit" to false, "id" to "", "form" to object : UTSJSONObject() {
            var contact_name = ""
            var contact_phone = ""
            var address = ""
            var detail = ""
            var is_default = false
        })
    }
    open var fetchAddressDetail = ::gen_fetchAddressDetail_fn
    open fun gen_fetchAddressDetail_fn(id: String) {
        val token = uni_getStorageSync("token")
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/addresses/" + id, method = "GET", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
        }, success = fun(res: Any){
            if (res.data.success && res.data.data.address) {
                val addr = res.data.data.address
                this.form = object : UTSJSONObject() {
                    var contact_name = addr.name || addr.contact_name
                    var contact_phone = addr.phone || addr.contact_phone
                    var address = addr.detail || addr.address
                    var detail = ""
                    var is_default = !!addr.is_default
                }
            }
        }
        ))
    }
    open var onSwitchChange = ::gen_onSwitchChange_fn
    open fun gen_onSwitchChange_fn(e: Any) {
        this.form.is_default = e.detail.value
    }
    open var saveAddress = ::gen_saveAddress_fn
    open fun gen_saveAddress_fn() {
        if (!this.form.contact_name || !this.form.contact_phone || !this.form.address) {
            uni_showToast(ShowToastOptions(title = "请填写完整信息", icon = "none"))
            return
        }
        if (!UTSRegExp("^1\\d{10}\$", "").test(this.form.contact_phone)) {
            uni_showToast(ShowToastOptions(title = "手机号格式不正确", icon = "none"))
            return
        }
        val token = uni_getStorageSync("token")
        val url = if (this.isEdit) {
            "" + BASE_URL + "/addresses/" + this.id
        } else {
            "" + BASE_URL + "/addresses"
        }
        val method = if (this.isEdit) {
            "PUT"
        } else {
            "POST"
        }
        uni_request<Any>(RequestOptions(url = url, method = method, header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = this.form, success = fun(res: Any){
            if (res.data.success) {
                uni_showToast(ShowToastOptions(title = "保存成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1500)
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "保存失败", icon = "none"))
            }
        }
        ))
    }
    open var deleteAddress = ::gen_deleteAddress_fn
    open fun gen_deleteAddress_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要删除这个地址吗？", success = fun(res){
            if (res.confirm) {
                val token = uni_getStorageSync("token")
                uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/addresses/" + this.id, method = "DELETE", header = object : UTSJSONObject() {
                    var Authorization = "Bearer " + token
                }, success = fun(res: Any){
                    if (res.data.success) {
                        uni_showToast(ShowToastOptions(title = "删除成功", icon = "success"))
                        setTimeout(fun(){
                            uni_navigateBack(null)
                        }
                        , 1500)
                    }
                }
                ))
            }
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx")), "form-card" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "30rpx", "borderTopRightRadius" to "30rpx", "borderBottomRightRadius" to "30rpx", "borderBottomLeftRadius" to "30rpx", "paddingTop" to 0, "paddingRight" to "30rpx", "paddingBottom" to 0, "paddingLeft" to "30rpx", "boxShadow" to "0 4rpx 16rpx rgba(255, 105, 180, 0.1)")), "form-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "36rpx", "paddingRight" to 0, "paddingBottom" to "36rpx", "paddingLeft" to 0, "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "#FFF0F5", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "label" to _pS(_uM("width" to "180rpx", "fontSize" to "30rpx", "color" to "#5D4E6D")), "input" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#333333")), "placeholder" to _pS(_uM("color" to "#CCCCCC")), "switch-item" to _pS(_uM("justifyContent" to "space-between")), "footer-btns" to _pS(_uM("marginTop" to "60rpx")), "save-btn" to _pS(_uM("backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "height" to "88rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopWidth" to "medium", "borderRightWidth" to "medium", "borderBottomWidth" to "medium", "borderLeftWidth" to "medium", "borderTopStyle" to "none", "borderRightStyle" to "none", "borderBottomStyle" to "none", "borderLeftStyle" to "none", "borderTopColor" to "#000000", "borderRightColor" to "#000000", "borderBottomColor" to "#000000", "borderLeftColor" to "#000000", "boxShadow" to "0 8rpx 20rpx rgba(255, 105, 180, 0.3)", "marginBottom" to "30rpx", "transform:active" to "scale(0.98)")), "save-text" to _pS(_uM("color" to "#FFFFFF", "fontSize" to "32rpx", "fontWeight" to "bold", "letterSpacing" to 1)), "delete-btn" to _pS(_uM("backgroundColor" to "#FFFFFF", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "height" to "88rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopWidth" to "2rpx", "borderRightWidth" to "2rpx", "borderBottomWidth" to "2rpx", "borderLeftWidth" to "2rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "backgroundColor:active" to "#FFF0F5")), "delete-text" to _pS(_uM("color" to "#FF69B4", "fontSize" to "32rpx", "fontWeight" to "bold")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
