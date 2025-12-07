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
import io.dcloud.uniapp.extapi.chooseImage as uni_chooseImage
import io.dcloud.uniapp.extapi.getStorageSync as uni_getStorageSync
import io.dcloud.uniapp.extapi.hideLoading as uni_hideLoading
import io.dcloud.uniapp.extapi.navigateBack as uni_navigateBack
import io.dcloud.uniapp.extapi.request as uni_request
import io.dcloud.uniapp.extapi.setStorageSync as uni_setStorageSync
import io.dcloud.uniapp.extapi.showLoading as uni_showLoading
import io.dcloud.uniapp.extapi.showToast as uni_showToast
import io.dcloud.uniapp.extapi.uploadFile as uni_uploadFile
open class GenPagesSettingsProfile : BasePage {
    constructor(__ins: ComponentInternalInstance, __renderer: String?) : super(__ins, __renderer) {
        onLoad(fun(_: OnLoadOptions) {
            this.loadUserInfo()
        }
        , __ins)
    }
    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    override fun `$render`(): Any? {
        val _ctx = this
        val _cache = this.`$`.renderCache
        return _cE("view", _uM("class" to "container"), _uA(
            _cE("view", _uM("class" to "avatar-section", "onClick" to _ctx.chooseAvatar), _uA(
                _cE("view", _uM("class" to "avatar-wrapper"), _uA(
                    _cE("image", _uM("class" to "avatar", "src" to (_ctx.userInfo.avatar || _ctx.defaultAvatarImage), "mode" to "aspectFill"), null, 8, _uA(
                        "src"
                    )),
                    _cE("view", _uM("class" to "avatar-edit"), _uA(
                        _cE("text", _uM("class" to "edit-text"), "编辑")
                    ))
                )),
                _cE("text", _uM("class" to "avatar-tip"), "点击更换头像")
            ), 8, _uA(
                "onClick"
            )),
            _cE("view", _uM("class" to "form-section"), _uA(
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "昵称"),
                    _cE("input", _uM("class" to "input", "type" to "text", "modelValue" to _ctx.form.nickname, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.nickname = `$event`.detail.value
                    }
                    , "placeholder" to "请输入昵称", "placeholder-class" to "placeholder"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                )),
                _cE("view", _uM("class" to "form-item"), _uA(
                    _cE("text", _uM("class" to "label"), "手机号"),
                    _cE("input", _uM("class" to "input", "type" to "number", "modelValue" to _ctx.form.phone, "onInput" to fun(`$event`: UniInputEvent){
                        _ctx.form.phone = `$event`.detail.value
                    }
                    , "placeholder" to "请输入手机号", "placeholder-class" to "placeholder", "maxlength" to "11"), null, 40, _uA(
                        "modelValue",
                        "onInput"
                    ))
                ))
            )),
            _cE("view", _uM("class" to "submit-btn", "onClick" to _ctx.handleSubmit), _uA(
                _cE("text", _uM("class" to "submit-text"), "保存")
            ), 8, _uA(
                "onClick"
            ))
        ))
    }
    open var defaultAvatarImage: Any? by `$data`
    open var userInfo: UTSJSONObject by `$data`
    open var form: UTSJSONObject by `$data`
    open var avatarFile: Any by `$data`
    @Suppress("USELESS_CAST")
    override fun data(): Map<String, Any?> {
        return _uM("defaultAvatarImage" to DEFAULT_AVATAR_IMAGE, "userInfo" to object : UTSJSONObject() {
            var avatar = ""
            var nickname = ""
            var phone = ""
        }, "form" to object : UTSJSONObject() {
            var nickname = ""
            var phone = ""
        }, "avatarFile" to null as Any)
    }
    open var loadUserInfo = ::gen_loadUserInfo_fn
    open fun gen_loadUserInfo_fn() {
        val userInfo = uni_getStorageSync("userInfo")
        if (userInfo) {
            this.userInfo = userInfo
            this.form.nickname = userInfo.nickname || ""
            this.form.phone = userInfo.phone || ""
        }
        val token = uni_getStorageSync("token")
        if (token) {
            uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/auth/profile", method = "GET", header = object : UTSJSONObject() {
                var Authorization = "Bearer " + token
            }, success = fun(res: Any){
                if (res.data.success && res.data.data.user) {
                    val user = res.data.data.user
                    this.userInfo = user
                    this.form.nickname = user.nickname || ""
                    this.form.phone = user.phone || ""
                }
            }
            ))
        }
    }
    open var chooseAvatar = ::gen_chooseAvatar_fn
    open fun gen_chooseAvatar_fn() {
        uni_chooseImage(ChooseImageOptions(count = 1, sizeType = _uA(
            "compressed"
        ), sourceType = _uA(
            "album",
            "camera"
        ), success = fun(res){
            val tempFilePath = res.tempFilePaths[0]
            this.userInfo.avatar = tempFilePath
            this.avatarFile = tempFilePath
        }
        ))
    }
    open var handleSubmit = ::gen_handleSubmit_fn
    open fun gen_handleSubmit_fn() {
        val token = uni_getStorageSync("token")
        if (this.avatarFile) {
            uni_showLoading(ShowLoadingOptions(title = "上传头像..."))
            uni_uploadFile(UploadFileOptions(url = "" + BASE_URL + "/auth/upload-avatar", filePath = this.avatarFile, name = "avatar", header = object : UTSJSONObject() {
                var Authorization = "Bearer " + token
            }, success = fun(uploadRes: Any){
                val data = JSON.parse(uploadRes.data)
                if (data.success && data.data.avatar) {
                    this.userInfo.avatar = data.data.avatar
                }
                this.updateProfile()
            }, fail = fun(_){
                uni_hideLoading()
                uni_showToast(ShowToastOptions(title = "头像上传失败", icon = "none"))
            }))
        } else {
            this.updateProfile()
        }
    }
    open var updateProfile = ::gen_updateProfile_fn
    open fun gen_updateProfile_fn() {
        val token = uni_getStorageSync("token")
        uni_showLoading(ShowLoadingOptions(title = "保存中..."))
        val requestData: UTSJSONObject = _uO("nickname" to this.form.nickname, "phone" to this.form.phone)
        console.log("Updating profile with data:", requestData)
        uni_request<Any>(RequestOptions(url = "" + BASE_URL + "/auth/profile", method = "PUT", header = object : UTSJSONObject() {
            var Authorization = "Bearer " + token
            var `Content-Type` = "application/json"
        }, data = requestData, success = fun(res: Any){
            console.log("Profile update response:", res.data)
            if (res.data.success) {
                val serverUser = res.data.data.user || UTSJSONObject()
                val updatedUserInfo = UTSJSONObject.assign<UTSJSONObject>(UTSJSONObject(), this.userInfo, serverUser, _uO("nickname" to (serverUser.nickname || this.form.nickname), "phone" to (serverUser.phone || this.form.phone))) as UTSJSONObject
                uni_setStorageSync("userInfo", updatedUserInfo)
                uni_showToast(ShowToastOptions(title = "保存成功", icon = "success"))
                setTimeout(fun(){
                    uni_navigateBack(null)
                }, 1500)
            } else {
                uni_showToast(ShowToastOptions(title = res.data.message || "保存失败", icon = "none"))
            }
        }
        , fail = fun(err: Any){
            console.error("Profile update failed:", err)
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
                return _uM("container" to _pS(_uM("backgroundColor" to "#FFF5F8", "paddingTop" to "30rpx", "paddingRight" to "24rpx", "paddingBottom" to "30rpx", "paddingLeft" to "24rpx")), "avatar-section" to _pS(_uM("display" to "flex", "flexDirection" to "column", "alignItems" to "center", "paddingTop" to "40rpx", "paddingRight" to "40rpx", "paddingBottom" to "40rpx", "paddingLeft" to "40rpx", "marginBottom" to "30rpx")), "avatar-wrapper" to _pS(_uM("position" to "relative", "width" to "180rpx", "height" to "180rpx")), "avatar" to _pS(_uM("width" to "180rpx", "height" to "180rpx", "borderTopWidth" to "6rpx", "borderRightWidth" to "6rpx", "borderBottomWidth" to "6rpx", "borderLeftWidth" to "6rpx", "borderTopStyle" to "solid", "borderRightStyle" to "solid", "borderBottomStyle" to "solid", "borderLeftStyle" to "solid", "borderTopColor" to "rgba(255,255,255,0.8)", "borderRightColor" to "rgba(255,255,255,0.8)", "borderBottomColor" to "rgba(255,255,255,0.8)", "borderLeftColor" to "rgba(255,255,255,0.8)", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.3)")), "avatar-edit" to _pS(_uM("position" to "absolute", "bottom" to 0, "left" to 0, "right" to 0, "height" to "50rpx", "backgroundColor" to "rgba(0,0,0,0.5)", "borderTopLeftRadius" to 0, "borderTopRightRadius" to 0, "borderBottomRightRadius" to "90rpx", "borderBottomLeftRadius" to "90rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center")), "edit-text" to _pS(_uM("fontSize" to "22rpx", "color" to "#FFFFFF")), "avatar-tip" to _pS(_uM("marginTop" to "20rpx", "fontSize" to "26rpx", "color" to "#B8A9C9")), "form-section" to _pS(_uM("backgroundColor" to "rgba(255,255,255,0.9)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "overflow" to "hidden", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.1)", "backdropFilter" to "blur(10px)")), "form-item" to _pS(_uM("display" to "flex", "flexDirection" to "row", "alignItems" to "center", "paddingTop" to "36rpx", "paddingRight" to "40rpx", "paddingBottom" to "36rpx", "paddingLeft" to "40rpx", "borderBottomWidth" to 1, "borderBottomStyle" to "solid", "borderBottomColor" to "rgba(255,228,236,0.6)", "borderBottomWidth:last-child" to "medium", "borderBottomStyle:last-child" to "none", "borderBottomColor:last-child" to "#000000")), "label" to _pS(_uM("width" to "140rpx", "fontSize" to "30rpx", "color" to "#5D4E6D")), "input" to _pS(_uM("flex" to 1, "fontSize" to "30rpx", "color" to "#5D4E6D")), "placeholder" to _pS(_uM("color" to "#B8A9C9")), "submit-btn" to _pS(_uM("marginTop" to "60rpx", "paddingTop" to "32rpx", "paddingRight" to "32rpx", "paddingBottom" to "32rpx", "paddingLeft" to "32rpx", "backgroundImage" to "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)", "backgroundColor" to "rgba(0,0,0,0)", "borderTopLeftRadius" to "40rpx", "borderTopRightRadius" to "40rpx", "borderBottomRightRadius" to "40rpx", "borderBottomLeftRadius" to "40rpx", "display" to "flex", "justifyContent" to "center", "alignItems" to "center", "boxShadow" to "0 8rpx 24rpx rgba(255, 105, 180, 0.3)", "transform:active" to "scale(0.98)")), "submit-text" to _pS(_uM("fontSize" to "32rpx", "color" to "#FFFFFF", "letterSpacing" to 2)))
            }
        var inheritAttrs = true
        var inject: Map<String, Map<String, Any?>> = _uM()
        var emits: Map<String, Any?> = _uM()
        var props = _nP(_uM())
        var propsNeedCastKeys: UTSArray<String> = _uA()
        var components: Map<String, CreateVueComponent> = _uM()
    }
}
