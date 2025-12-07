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
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesLoginLogin : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {}
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "header"), _uA(
                _cE("image", _uM("class" to "logo", "src" to "/static/icons/icon-user.svg", "mode" to "aspectFit")),
                _cE("text", _uM("class" to "app-name"), "♡ 美味甜心 ♡"),
                _cE("text", _uM("class" to "app-desc"), "新鲜食材，用心烹饪")
            )),
            _cE("view", _uM("class" to "form"), _uA(
                _cE("view", _uM("class" to "input-group"), _uA(
                    _cE("image", _uM("class" to "input-icon", "src" to "/static/icons/icon-phone.svg", "mode" to "aspectFit")),
                    _cE("input", _uM("class" to "input", "type" to "number", "placeholder" to "请输入手机号", "modelValue" to _ctx.phone, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.phone = `$event`.detail.value
                    }
                    ), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "input-group"), _uA(
                    _cE("image", _uM("class" to "input-icon", "src" to "/static/icons/icon-lock.svg", "mode" to "aspectFit")),
                    _cE("input", _uM("class" to "input", "type" to "password", "placeholder" to "请输入密码", "modelValue" to _ctx.password, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.password = `$event`.detail.value
                    }
                    ), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "login-btn", "onClick" to _ctx.handleLogin), _uA(
                    _cE("text", _uM("class" to "login-btn-text"), "登 录 ♡")
                ), 8, _uA(
                    "onClick"
                )),
                _cE("view", _uM("class" to "register-link", "onClick" to _ctx.goToRegister), _uA(
                    _cE("text", _uM("class" to "register-text"), "还没有账号？立即注册")
                ), 8, _uA(
                    "onClick"
                ))
            )),
            _cE("view", _uM("class" to "footer"), _uA(
                _cE("text", _uM("class" to "footer-text"), "登录即表示同意"),
                _cE("text", _uM("class" to "link-text"), "《用户协议》"),
                _cE("text", _uM("class" to "footer-text"), "和"),
                _cE("text", _uM("class" to "link-text"), "《隐私政策》")
            ))
        ))
    }
    open var phone: String by `$data`
    open var password: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("phone" to "", "password" to "")
    }
    open var handleLogin = ::gen_handleLogin_fn
    open fun gen_handleLogin_fn() {
        if (!this.phone) {
            uni_showToast(ShowToastOptions(title = "请输入手机号", icon = "none"))
            return
        }
        if (!this.password) {
            uni_showToast(ShowToastOptions(title = "请输入密码", icon = "none"))
            return
        }
        uni_showLoading(ShowLoadingOptions(title = "登录中..."))
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/auth/login", method = "POST", header = object : UTSJSONObject() {
            var `Content-Type` = "application/json"
        }, data = _uO("phone" to this.phone, "password" to this.password), success = fun(res: Any){
            if (res.data.success && res.data.data.token) {
                uni_setStorageSync("token", res.data.data.token)
                uni_setStorageSync("userInfo", res.data.data.user)
                uni_showToast(ShowToastOptions(title = "登录成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1500)
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "登录失败", icon = "none"))
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
    open var goToRegister = ::gen_goToRegister_fn
    open fun gen_goToRegister_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/register/register"))
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
                return _uM("container" to _pS(_uM("display" to "flex", "flexDirection" to "column", "backgroundImage" to "linear-gradient(180deg, #FFE4EC 0%, #FFF5F8 100%)", "backgroundColor" to "rgba(0,0,0,0)", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx")), "header" to _pS(_uM("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to "80rpx", "paddingRight" to 0, "paddingBottom" to "60rpx", "paddingLeft" to 0)), "logo" to _pS(_uM("width" to "200rpx", "height" to "200rpx", "filter" to "drop-shadow(0 10rpx 20rpx rgba(255, 105, 180, 0.3))", "animation" to "float 3s ease-in-out infinite")), "app-name" to _pS(_uM("fontSize" to "52rpx", "color" to "#FF69B4", "marginTop" to "40rpx", "letterSpacing" to 4, "textShadow" to "2px 2px 0px #FFF, 4px 4px 0px rgba(255, 182, 193, 0.5)")), "app-desc" to _pS(_uM("fontSize" to "30rpx", "color" to "#B8A9C9", "marginTop" to "20rpx", "letterSpacing" to 1)), "form" to _pS(_uM("flex" to 1, "paddingTop" to "40rpx", "paddingRight" to "20rpx", "paddingBottom" to "40rpx", "paddingLeft" to "20rpx")), "input-group" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "30rpx", "paddingRight" to "36rpx", "paddingBottom" to "30rpx", "paddingLeft" to "36rpx", "backgroundColor" to "rgba(255,255,255,0.8)", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "marginBottom" to "40rpx", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)", "transitionProperty" to "all", "transitionDuration" to "0.3s", "transitionTimingFunction" to "ease", "borderTopColor:focus-within" to "#FF69B4", "borderRightColor:focus-within" to "#FF69B4", "borderBottomColor:focus-within" to "#FF69B4", "borderLeftColor:focus-within" to "#FF69B4", "backgroundColor:focus-within" to "#FFFFFF", "boxShadow:focus-within" to "0 12rpx 30rpx rgba(255, 105, 180, 0.25)", "transform:focus-within" to "translateY(-2px)")), "input-icon" to _pS(_uM("width" to "48rpx", "height" to "48rpx", "marginRight" to "24rpx")), "input" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#5D4E6D")), "login-btn" to _pS(_uM("marginTop" to "80rpx", "paddingTop" to "36rpx", "paddingRight" to "36rpx", "paddingBottom" to "36rpx", "paddingLeft" to "36rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "boxShadow" to "0 10rpx 30rpx rgba(255, 105, 180, 0.4)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.98)", "boxShadow:active" to "0 6rpx 20rpx rgba(255, 105, 180, 0.3)")), "login-btn-text" to _pS(_uM("fontSize" to "36rpx", "color" to "#ffffff", "letterSpacing" to 6)), "register-link" to _pS(_uM("marginTop" to "50rpx", "display" to "flex", "justifyContent" to "center")), "register-text" to _pS(_uM("fontSize" to "28rpx", "color" to "#FF69B4", "textDecoration" to "underline", "textDecorationColor" to "rgba(255, 105, 180, 0.3)")), "footer" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "center", "flexWrap" to "wrap", "paddingTop" to "40rpx", "paddingRight" to 0, "paddingBottom" to "40rpx", "paddingLeft" to 0, "opacity" to 0.8)), "footer-text" to _pS(_uM("fontSize" to "24rpx", "color" to "#B8A9C9")), "link-text" to _pS(_uM("fontSize" to "24rpx", "color" to "#FF69B4", "marginTop" to 0, "marginRight" to "8rpx", "marginBottom" to 0, "marginLeft" to "8rpx")), "@FONT-FACE" to _uM("0" to _uM()), "@TRANSITION" to _uM("input-group" to _uM("property" to "all", "duration" to "0.3s", "timingFunction" to "ease"), "login-btn" to _uM("property" to "transform", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
