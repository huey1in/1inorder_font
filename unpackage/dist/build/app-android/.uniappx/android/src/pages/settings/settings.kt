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
import io.dcloud.uniapp.extapi.clearStorageSync as uni_clearStorageSync
import io.dcloud.uniapp.extapi.getStorageInfoSync as uni_getStorageInfoSync
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
open class GenPagesSettingsSettings : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.getCacheSize()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "section"), _uA(
                _cE("view", _uM("class" to "section-header"), _uA(
                    _cE("text", _uM("class" to "section-title"), "账号安全")
                )),
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.goChangePassword), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-password.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "修改密码"),
                    _cE("text", _uM("class" to "menu-arrow"), "➜")
                ), 8, _uA(
                    "onClick"
                )),
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.goEditProfile), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-profile.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "个人资料"),
                    _cE("text", _uM("class" to "menu-arrow"), "➜")
                ), 8, _uA(
                    "onClick"
                ))
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("view", _uM("class" to "section-header"), _uA(
                    _cE("text", _uM("class" to "section-title"), "关于")
                )),
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.goAgreement), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-agreement.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "用户协议"),
                    _cE("text", _uM("class" to "menu-arrow"), "➜")
                ), 8, _uA(
                    "onClick"
                )),
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.goPrivacy), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-privacy.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "隐私政策"),
                    _cE("text", _uM("class" to "menu-arrow"), "➜")
                ), 8, _uA(
                    "onClick"
                )),
                _cE("view", _uM("class" to "menu-item"), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-version.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "当前版本"),
                    _cE("text", _uM("class" to "menu-value"), "v1.0.0")
                ))
            )),
            _cE("view", _uM("class" to "section"), _uA(
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.clearCache), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-clear.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "清除缓存"),
                    _cE("text", _uM("class" to "menu-value"), _tD(_ctx.cacheSize), 1)
                ), 8, _uA(
                    "onClick"
                ))
            ))
        ))
    }
    open var cacheSize: String by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("cacheSize" to "0KB")
    }
    open var getCacheSize = ::gen_getCacheSize_fn
    open fun gen_getCacheSize_fn() {
        try {
            val info = uni_getStorageInfoSync()
            if (info && info.currentSize) {
                val size = info.currentSize
                if (size < 1024) {
                    this.cacheSize = size + "KB"
                } else {
                    this.cacheSize = (size / 1024).toFixed(2) + "MB"
                }
            }
        }
         catch (e: Throwable) {
            this.cacheSize = "0KB"
        }
    }
    open var goChangePassword = ::gen_goChangePassword_fn
    open fun gen_goChangePassword_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/settings/password"))
    }
    open var goEditProfile = ::gen_goEditProfile_fn
    open fun gen_goEditProfile_fn() {
        val token = uni_getStorageSync("token")
        if (!token) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/settings/profile"))
    }
    open var goAgreement = ::gen_goAgreement_fn
    open fun gen_goAgreement_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/legal/agreement"))
    }
    open var goPrivacy = ::gen_goPrivacy_fn
    open fun gen_goPrivacy_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/legal/privacy"))
    }
    open var clearCache = ::gen_clearCache_fn
    open fun gen_clearCache_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要清除缓存吗？", success = fun(res){
            if (res.confirm) {
                val token = uni_getStorageSync("token")
                val userInfo = uni_getStorageSync("userInfo")
                uni_clearStorageSync()
                if (token) {
                    uni_setStorageSync("token", token)
                }
                if (userInfo) {
                    uni_setStorageSync("userInfo", userInfo)
                }
                this.cacheSize = "0KB"
                uni_showToast(ShowToastOptions(title = "缓存已清除", icon = "success"))
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingTop" to "20rpx", "paddingRight" to 0, "paddingBottom" to "20rpx", "paddingLeft" to 0)), "section" to _pS(_uM("marginTop" to "20rpx", "marginRight" to "24rpx", "marginBottom" to "20rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "overflow" to "hidden", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "section-header" to _pS(_uM("paddingTop" to "30rpx", "paddingRight" to "36rpx", "paddingBottom" to "30rpx", "paddingLeft" to "36rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "backgroundColor" to "rgba(255,240,245,0.5)")), "section-title" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "menu-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "36rpx", "paddingRight" to "40rpx", "paddingBottom" to "36rpx", "paddingLeft" to "40rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "transitionProperty" to "backgroundColor", "transitionDuration" to "0.2s", "backgroundColor:active" to "#FFF0F5", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "menu-icon" to _pS(_uM("width" to "48rpx", "height" to "48rpx", "marginRight" to "24rpx")), "menu-text" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#5D4E6D")), "menu-arrow" to _pS(_uM("fontSize" to "34rpx", "color" to "#B8A9C9")), "menu-value" to _pS(_uM("fontSize" to "28rpx", "color" to "#B8A9C9")), "@TRANSITION" to _uM("menu-item" to _uM("property" to "backgroundColor", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
