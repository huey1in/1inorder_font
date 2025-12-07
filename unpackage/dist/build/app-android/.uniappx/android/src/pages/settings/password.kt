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
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSettingsPassword : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "form-section"), _uA(
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "当前密码"),
                    _cE("input", _uM("class" to "input", "type" to "password", "modelValue" to _ctx.form.oldPassword, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.oldPassword = `$event`.detail.value
                    }
                    , "placeholder" to "请输入当前密码", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "新密码"),
                    _cE("input", _uM("class" to "input", "type" to "password", "modelValue" to _ctx.form.newPassword, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.newPassword = `$event`.detail.value
                    }
                    , "placeholder" to "请输入新密码（6-20位）", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "确认密码"),
                    _cE("input", _uM("class" to "input", "type" to "password", "modelValue" to _ctx.form.confirmPassword, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.confirmPassword = `$event`.detail.value
                    }
                    , "placeholder" to "请再次输入新密码", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                ))
            )),
            _cE("view", _uM("class" to "submit-btn", "onClick" to _ctx.handleSubmit), _uA(
                _cE("text", _uM("class" to "submit-text"), "确认修改")
            ), 8, _uA(
                "onClick"
            ))
        ))
    }
    open var form: UTSJSONObject by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("form" to object : UTSJSONObject() {
            var oldPassword = ""
            var newPassword = ""
            var confirmPassword = ""
        })
    }
    open var handleSubmit = ::gen_handleSubmit_fn
    open fun gen_handleSubmit_fn() {
        if (!this.form.oldPassword) {
            uni_showToast(ShowToastOptions(title = "请输入当前密码", icon = "none"))
            return
        }
        if (!this.form.newPassword) {
            uni_showToast(ShowToastOptions(title = "请输入新密码", icon = "none"))
            return
        }
        if (this.form.newPassword.length < 6 || this.form.newPassword.length > 20) {
            uni_showToast(ShowToastOptions(title = "密码长度为6-20位", icon = "none"))
            return
        }
        if (this.form.newPassword !== this.form.confirmPassword) {
            uni_showToast(ShowToastOptions(title = "两次密码输入不一致", icon = "none"))
            return
        }
        val token = uni_getStorageSync("token")
        uni_showLoading(ShowLoadingOptions(title = "提交中..."))
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/auth/change-password", method = "POST", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = _uO("currentPassword" to this.form.oldPassword, "newPassword" to this.form.newPassword), success = fun(res: Any){
            if (res.data.success) {
                uni_showToast(ShowToastOptions(title = "密码修改成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1500)
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "修改失败", icon = "none"))
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingTop" to "30rpx", "paddingRight" to "24rpx", "paddingBottom" to "30rpx", "paddingLeft" to "24rpx")), "form-section" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "overflow" to "hidden", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "form-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "36rpx", "paddingRight" to "40rpx", "paddingBottom" to "36rpx", "paddingLeft" to "40rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "label" to _pS(_uM("width" to "160rpx", "fontSize" to "30rpx", "color" to "#5D4E6D")), "input" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#5D4E6D")), "placeholder" to _pS(_uM("color" to "#B8A9C9")), "submit-btn" to _pS(_uM("marginTop" to "60rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.3)", "transform:active" to "scale(0.98)")), "submit-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FFFFFF", "letterSpacing" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
