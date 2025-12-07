
	import { BASE_URL } from '@/common/config.uts'

	const __sfc__ = defineComponent({
		data() {
			return {
				phone: '',
				password: '',
				confirmPassword: ''
			}
		},
		methods: {
			async handleRegister() {
				if (!this.phone) {
					uni.showToast({ title: '请输入手机号', icon: 'none' })
					return
				}
				if (!this.password) {
					uni.showToast({ title: '请输入密码', icon: 'none' })
					return
				}
				if (this.password.length < 6 || this.password.length > 20) {
					uni.showToast({ title: '密码长度为6-20位', icon: 'none' })
					return
				}
				if (this.password !== this.confirmPassword) {
					uni.showToast({ title: '两次输入密码不一致', icon: 'none' })
					return
				}

				let loadingShown = false
				try {
					uni.showLoading({ title: '注册中...' })
					loadingShown = true

					const res: any = await new Promise((resolve, reject) => {
						console.log('注册请求 payload:', {
							phone: this.phone,
							password: this.password
						})
						uni.request({
							url: `${BASE_URL}/auth/register`,
							method: 'POST',
							header: {
								'Content-Type': 'application/json'
							},
							data: {
								phone: this.phone,
								password: this.password
							},
							success: (resp) => {
								console.log('注册响应:', resp)
								resolve(resp)
							},
							fail: reject
						})
					})

					if ((res.statusCode === 200 || res.statusCode === 201) && res.data && res.data.success) {
						if (res.data.data && res.data.data.token) {
							uni.setStorageSync('token', res.data.data.token)
							if (res.data.data.user) {
								uni.setStorageSync('userInfo', res.data.data.user)
							}
						}
						uni.showToast({ title: '注册成功', icon: 'success' })
						setTimeout(() => {
							uni.reLaunch({ url: '/pages/index/index' })
						}, 1200)
					} else {
						const msg = res.data?.message || `注册失败（${res.statusCode || '未知错误'}）`
						uni.showToast({ title: msg, icon: 'none' })
					}
				} catch (error) {
					uni.showToast({ title: '网络错误', icon: 'none' })
				} finally {
					if (loadingShown) {
						uni.hideLoading()
					}
				}
			},

			goToLogin() {
				const pages = getCurrentPages()
				if (pages.length > 1) {
					uni.navigateBack()
				} else {
					uni.redirectTo({ url: '/pages/login/login' })
				}
			},

			goAgreement() {
				uni.navigateTo({ url: '/pages/legal/agreement' })
			},

			goPrivacy() {
				uni.navigateTo({ url: '/pages/legal/privacy' })
			}
		}
	})

export default __sfc__
function GenPagesRegisterRegisterRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "form" }), [
      _cE("view", _uM({ class: "input-group" }), [
        _cE("image", _uM({
          class: "input-icon",
          src: "/static/icons/icon-phone.svg",
          mode: "aspectFit"
        })),
        _cE("input", _uM({
          class: "input",
          type: "number",
          placeholder: "请输入手机号",
          modelValue: _ctx.phone,
          onInput: ($event: UniInputEvent) => {(_ctx.phone) = $event.detail.value}
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "input-group" }), [
        _cE("image", _uM({
          class: "input-icon",
          src: "/static/icons/icon-lock.svg",
          mode: "aspectFit"
        })),
        _cE("input", _uM({
          class: "input",
          type: "password",
          placeholder: "设置登录密码（6-20位）",
          modelValue: _ctx.password,
          onInput: ($event: UniInputEvent) => {(_ctx.password) = $event.detail.value}
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "input-group" }), [
        _cE("image", _uM({
          class: "input-icon",
          src: "/static/icons/icon-lock.svg",
          mode: "aspectFit"
        })),
        _cE("input", _uM({
          class: "input",
          type: "password",
          placeholder: "再次输入密码",
          modelValue: _ctx.confirmPassword,
          onInput: ($event: UniInputEvent) => {(_ctx.confirmPassword) = $event.detail.value}
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({
        class: "login-btn",
        onClick: _ctx.handleRegister
      }), [
        _cE("text", _uM({ class: "login-btn-text" }), "注 册")
      ], 8 /* PROPS */, ["onClick"]),
      _cE("view", _uM({
        class: "register-link",
        onClick: _ctx.goToLogin
      }), [
        _cE("text", _uM({ class: "register-text" }), "已有账号？去登录")
      ], 8 /* PROPS */, ["onClick"])
    ]),
    _cE("view", _uM({ class: "footer" }), [
      _cE("text", _uM({ class: "footer-text" }), "注册即表示同意"),
      _cE("text", _uM({
        class: "link-text",
        onClick: withModifiers(_ctx.goAgreement, ["stop"])
      }), "《用户协议》", 8 /* PROPS */, ["onClick"]),
      _cE("text", _uM({ class: "footer-text" }), "和"),
      _cE("text", _uM({
        class: "link-text",
        onClick: withModifiers(_ctx.goPrivacy, ["stop"])
      }), "《隐私政策》", 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesRegisterRegisterStyles = [_uM([["container", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["backgroundImage", "linear-gradient(180deg, #FFE4EC 0%, #FFF5F8 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["boxSizing", "border-box"]]))], ["form", _pS(_uM([["flex", 1], ["paddingTop", "40rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "20rpx"], ["boxSizing", "border-box"]]))], ["input-group", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "30rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "36rpx"], ["backgroundColor", "rgba(255,255,255,0.8)"], ["borderTopWidth", 2], ["borderRightWidth", 2], ["borderBottomWidth", 2], ["borderLeftWidth", 2], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["marginBottom", "40rpx"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"], ["transitionProperty", "all"], ["transitionDuration", "0.3s"], ["transitionTimingFunction", "ease"], ["borderTopColor:focus-within", "#FF69B4"], ["borderRightColor:focus-within", "#FF69B4"], ["borderBottomColor:focus-within", "#FF69B4"], ["borderLeftColor:focus-within", "#FF69B4"], ["backgroundColor:focus-within", "#FFFFFF"], ["boxShadow:focus-within", "0 12rpx 30rpx rgba(255, 105, 180, 0.25)"], ["transform:focus-within", "translateY(-2px)"]]))], ["input-icon", _pS(_uM([["width", "48rpx"], ["height", "48rpx"], ["marginRight", "24rpx"]]))], ["input", _pS(_uM([["flex", 1], ["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["login-btn", _pS(_uM([["marginTop", "80rpx"], ["paddingTop", "36rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "36rpx"], ["paddingLeft", "36rpx"], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["boxShadow", "0 10rpx 30rpx rgba(255, 105, 180, 0.4)"], ["transitionProperty", "transform"], ["transitionDuration", "0.2s"], ["transform:active", "scale(0.98)"], ["boxShadow:active", "0 6rpx 20rpx rgba(255, 105, 180, 0.3)"]]))], ["login-btn-text", _pS(_uM([["fontSize", "36rpx"], ["color", "#ffffff"], ["letterSpacing", 6]]))], ["register-link", _pS(_uM([["marginTop", "50rpx"], ["display", "flex"], ["justifyContent", "flex-start"]]))], ["register-text", _pS(_uM([["fontSize", "28rpx"], ["color", "#FF69B4"], ["textDecoration", "underline"], ["textDecorationColor", "rgba(255, 105, 180, 0.3)"]]))], ["footer", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["justifyContent", "center"], ["flexWrap", "wrap"], ["paddingTop", "40rpx"], ["paddingRight", 0], ["paddingBottom", "40rpx"], ["paddingLeft", 0], ["opacity", 0.8]]))], ["footer-text", _pS(_uM([["fontSize", "24rpx"], ["color", "#B8A9C9"]]))], ["link-text", _pS(_uM([["fontSize", "24rpx"], ["color", "#FF69B4"], ["marginTop", 0], ["marginRight", "8rpx"], ["marginBottom", 0], ["marginLeft", "8rpx"]]))], ["@TRANSITION", _uM([["input-group", _uM([["property", "all"], ["duration", "0.3s"], ["timingFunction", "ease"]])], ["login-btn", _uM([["property", "transform"], ["duration", "0.2s"]])]])]])]
