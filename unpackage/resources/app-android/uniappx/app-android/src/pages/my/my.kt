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
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showModal as uni_showModal
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.switchTab as uni_switchTab
open class GenPagesMyMy : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onPageShow(fun() {
            this.checkLoginStatus()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to _nC(_uA(
                "user-header",
                if (_ctx.isLoggedIn) {
                    "logged-in"
                } else {
                    ""
                }
            ))), _uA(
                _cE("view", _uM("class" to "user-avatar"), _uA(
                    _cE("image", _uM("class" to "avatar-icon", "src" to (_ctx.userInfo.avatar || _ctx.defaultAvatarImage), "mode" to "aspectFit"), null, 8, _uA(
                        "src"
                    ))
                )),
                if (isTrue(_ctx.isLoggedIn)) {
                    _cE("view", _uM("key" to 0, "class" to "user-info"), _uA(
                        if (isTrue(_ctx.userInfo.nickname)) {
                            _cE("text", _uM("key" to 0, "class" to "nickname"), _tD(_ctx.userInfo.nickname), 1)
                        } else {
                            if (isTrue(_ctx.userInfo.username)) {
                                _cE("text", _uM("key" to 1, "class" to "username"), _tD(_ctx.userInfo.username), 1)
                            } else {
                                _cC("v-if", true)
                            }
                        },
                        _cE("text", _uM("class" to "phone"), _tD(_ctx.userInfo.phone), 1)
                    ))
                } else {
                    _cE("view", _uM("key" to 1, "class" to "login-prompt", "onClick" to _ctx.goLogin), _uA(
                        _cE("text", _uM("class" to "login-text"), "点击登录 ♡"),
                        _cE("text", _uM("class" to "login-arrow"), "›")
                    ), 8, _uA(
                        "onClick"
                    ))
                }
            ), 2),
            _cE("view", _uM("class" to "section menu-section"), _uA(
                _cE("view", _uM("class" to "menu-item", "onClick" to _ctx.goAddress), _uA(
                    _cE("image", _uM("class" to "menu-icon", "src" to "/static/icons/icon-address.svg", "mode" to "aspectFit")),
                    _cE("text", _uM("class" to "menu-text"), "收货地址"),
                    _cE("text", _uM("class" to "menu-arrow"), "›")
                ), 8, _uA(
                    "onClick"
                ))
            )),
            if (isTrue(_ctx.isLoggedIn)) {
                _cE("view", _uM("key" to 0, "class" to "logout-btn", "onClick" to _ctx.handleLogout), _uA(
                    _cE("text", _uM("class" to "logout-text"), "退出登录")
                ), 8, _uA(
                    "onClick"
                ))
            } else {
                _cC("v-if", true)
            }
        ))
    }
    open var isLoggedIn: Boolean by `$data`
    open var defaultAvatarImage: Any? by `$data`
    open var userInfo: UTSJSONObject by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("isLoggedIn" to false, "defaultAvatarImage" to DEFAULT_AVATAR_IMAGE, "userInfo" to object : UTSJSONObject() {
            var username = ""
            var nickname = ""
            var phone = ""
            var avatar = ""
        })
    }
    open var checkLoginStatus = ::gen_checkLoginStatus_fn
    open fun gen_checkLoginStatus_fn() {
        val token = uni_getStorageSync("token")
        val userInfo = uni_getStorageSync("userInfo")
        this.isLoggedIn = !!token
        if (userInfo) {
            this.userInfo = userInfo
        }
    }
    open var goLogin = ::gen_goLogin_fn
    open fun gen_goLogin_fn() {
        uni_navigateTo(NavigateToOptions(url = "/pages/login/login"))
    }
    open var goOrderList = ::gen_goOrderList_fn
    open fun gen_goOrderList_fn(status: String?) {
        if (!this.isLoggedIn) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            this.goLogin()
            return
        }
        if (status) {
            uni_setStorageSync("orderStatus", status)
        }
        uni_switchTab(SwitchTabOptions(url = "/pages/order/list"))
    }
    open var goAddress = ::gen_goAddress_fn
    open fun gen_goAddress_fn() {
        if (!this.isLoggedIn) {
            uni_showToast(ShowToastOptions(title = "请先登录", icon = "none"))
            this.goLogin()
            return
        }
        uni_navigateTo(NavigateToOptions(url = "/pages/address/list"))
    }
    open var handleLogout = ::gen_handleLogout_fn
    open fun gen_handleLogout_fn() {
        uni_showModal(ShowModalOptions(title = "提示", content = "确定要退出登录吗？", success = fun(res){
            if (res.confirm) {
                uni_removeStorageSync("token")
                uni_removeStorageSync("userInfo")
                this.isLoggedIn = false
                this.userInfo = object : UTSJSONObject() {
                    var username = ""
                    var phone = ""
                }
                uni_showToast(ShowToastOptions(title = "已退出登录", icon = "success"))
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8")), "user-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "80rpx", "paddingRight" to "40rpx", "paddingBottom" to "60rpx", "paddingLeft" to "40rpx", "backgroundImage" to "linear-gradient(135deg, #FF9A9E 0%, #FECFEF 99%, #FECFEF 100%)", "backgroundColor" to "rgba(0,0,0,0)", "boxShadow" to "0 10rpx 30rpx rgba(255, 105, 180, 0.2)", "position" to "relative", "overflow" to "hidden", "content::before" to "''", "position::before" to "absolute", "top::before" to "-50%", "right::before" to "-20%", "width::before" to "150%", "height::before" to "150%", "transform::before" to "rotate(-15deg)")), "user-avatar" to _pS(_uM("width" to "140rpx", "height" to "140rpx", "backgroundColor" to "#FFFFFF", "borderTopWidth" to "6rpx", "borderRightWidth" to "6rpx", "borderBottomWidth" to "6rpx", "borderLeftWidth" to "6rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.6)", "borderRightColor" to "rgba(255,255,255,0.6)", "borderBottomColor" to "rgba(255,255,255,0.6)", "borderLeftColor" to "rgba(255,255,255,0.6)", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.3)", "zIndex" to 1)), "avatar-icon" to _pS(_uM("width" to "90rpx", "height" to "90rpx")), "avatar-text" to _pS(_uM("fontSize" to "56rpx", "color" to "#FF69B4")), "user-info" to _pS(_uM("marginLeft" to "30rpx", "display" to "flex", "flexDirection" to "column", "zIndex" to 1)), "username" to _pS(_uM("fontSize" to "40rpx", "color" to "#FFFFFF", "letterSpacing" to 1, "textShadow" to "0 2rpx 4rpx rgba(0,0,0,0.1)")), "nickname" to _pS(_uM("fontSize" to "40rpx", "color" to "#FFFFFF", "letterSpacing" to 1, "textShadow" to "0 2rpx 4rpx rgba(0,0,0,0.1)")), "phone" to _pS(_uM("fontSize" to "28rpx", "color" to "rgba(255,255,255,0.9)", "marginTop" to "10rpx")), "login-prompt" to _pS(_uM("flex" to 1, "marginLeft" to "30rpx", "display" to "flex", "flexDirection" to "row", "alignItems" to "center", "zIndex" to 1)), "login-text" to _pS(_uM("fontSize" to "36rpx", "color" to "#FFFFFF", "letterSpacing" to 1)), "login-arrow" to _pS(_uM("fontSize" to "44rpx", "color" to "rgba(255,255,255,0.9)", "marginLeft" to "16rpx")), "section" to _pS(_uM("marginTop" to "30rpx", "marginRight" to "24rpx", "marginBottom" to "30rpx", "marginLeft" to "24rpx", "backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "overflow" to "hidden", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "section-header" to _pS(_uM("display" to "flex", "flexDirection" to "row", "justifyContent" to "space-between", "alignItems" to "center", "paddingTop" to "30rpx", "paddingRight" to "36rpx", "paddingBottom" to "30rpx", "paddingLeft" to "36rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "backgroundColor" to "rgba(255,240,245,0.5)")), "section-title" to _pS(_uM("fontSize" to "32rpx", "color" to "#5D4E6D")), "view-all" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "opacity" to 0.8)), "view-all-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#B8A9C9")), "arrow" to _pS(_uM("fontSize" to "30rpx", "color" to "#B8A9C9", "marginLeft" to "8rpx")), "order-status-list" to _pS(_uM("display" to "flex", "flexDirection" to "row", "paddingTop" to "40rpx", "paddingRight" to 0, "paddingBottom" to "40rpx", "paddingLeft" to 0)), "order-status-item" to _pS(_uM("flex" to 1, "display" to "flex", "flexDirection" to "column", "alignItems" to "center", "position" to "relative", "opacity:active" to 0.7)), "status-icon" to _pS(_uM("width" to "64rpx", "height" to "64rpx", "marginBottom" to "16rpx", "filter" to "drop-shadow(0 4rpx 8rpx rgba(255, 105, 180, 0.2))")), "status-text" to _pS(_uM("fontSize" to "26rpx", "color" to "#5D4E6D")), "menu-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "36rpx", "paddingRight" to "40rpx", "paddingBottom" to "36rpx", "paddingLeft" to "40rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "transitionProperty" to "backgroundColor", "transitionDuration" to "0.2s", "backgroundColor:active" to "#FFF0F5", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "menu-icon" to _pS(_uM("width" to "52rpx", "height" to "52rpx", "marginRight" to "24rpx")), "menu-text" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#5D4E6D")), "menu-arrow" to _pS(_uM("fontSize" to "34rpx", "color" to "#B8A9C9")), "logout-btn" to _pS(_uM("marginTop" to "60rpx", "marginRight" to "40rpx", "marginBottom" to "60rpx", "marginLeft" to "40rpx", "paddingTop" to "30rpx", "paddingRight" to "30rpx", "paddingBottom" to "30rpx", "paddingLeft" to "30rpx", "backgroundColor" to "#FFFFFF", "borderTopWidth" to 2, "borderRightWidth" to 2, "borderBottomWidth" to 2, "borderLeftWidth" to 2, "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "#FFB6C1", "borderRightColor" to "#FFB6C1", "borderBottomColor" to "#FFB6C1", "borderLeftColor" to "#FFB6C1", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 6rpx 20rpx rgba(255, 105, 180, 0.15)", "transitionProperty" to "transform", "transitionDuration" to "0.2s", "transform:active" to "scale(0.98)", "backgroundColor:active" to "#FFF0F5")), "logout-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FF69B4", "letterSpacing" to 1)), "@TRANSITION" to _uM("menu-item" to _uM("property" to "backgroundColor", "duration" to "0.2s"), "logout-btn" to _uM("property" to "transform", "duration" to "0.2s")))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
