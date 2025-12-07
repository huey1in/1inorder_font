
	const __sfc__ = defineComponent({
		data() {
			return {
				cacheSize: '0KB'
			}
		},
		onLoad() {
			this.getCacheSize()
		},
		methods: {
			getCacheSize() {
				// 获取缓存大小（模拟）
				try {
					const info = uni.getStorageInfoSync()
					if (info && info.currentSize) {
						const size = info.currentSize
						if (size < 1024) {
							this.cacheSize = size + 'KB'
						} else {
							this.cacheSize = (size / 1024).toFixed(2) + 'MB'
						}
					}
				} catch (e) {
					this.cacheSize = '0KB'
				}
			},
			
			goChangePassword() {
				const token = uni.getStorageSync('token')
				if (!token) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					uni.navigateTo({ url: '/pages/login/login' })
					return
				}
				uni.navigateTo({ url: '/pages/settings/password' })
			},
			
			goEditProfile() {
				const token = uni.getStorageSync('token')
				if (!token) {
					uni.showToast({ title: '请先登录', icon: 'none' })
					uni.navigateTo({ url: '/pages/login/login' })
					return
				}
				uni.navigateTo({ url: '/pages/settings/profile' })
			},
			
			goAgreement() {
				uni.navigateTo({ url: '/pages/legal/agreement' })
			},
			
			goPrivacy() {
				uni.navigateTo({ url: '/pages/legal/privacy' })
			},
			
			clearCache() {
				uni.showModal({
					title: '提示',
					content: '确定要清除缓存吗？',
					success: (res) => {
						if (res.confirm) {
							// 保留登录信息
							const token = uni.getStorageSync('token')
							const userInfo = uni.getStorageSync('userInfo')
							
							uni.clearStorageSync()
							
							// 恢复登录信息
							if (token) uni.setStorageSync('token', token)
							if (userInfo) uni.setStorageSync('userInfo', userInfo)
							
							this.cacheSize = '0KB'
							uni.showToast({ title: '缓存已清除', icon: 'success' })
						}
					}
				})
			}
		}
	})

export default __sfc__
function GenPagesSettingsSettingsRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "section" }), [
      _cE("view", _uM({ class: "section-header" }), [
        _cE("text", _uM({ class: "section-title" }), "账号安全")
      ]),
      _cE("view", _uM({
        class: "menu-item",
        onClick: _ctx.goChangePassword
      }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-password.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "修改密码"),
        _cE("text", _uM({ class: "menu-arrow" }), "➜")
      ], 8 /* PROPS */, ["onClick"]),
      _cE("view", _uM({
        class: "menu-item",
        onClick: _ctx.goEditProfile
      }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-profile.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "个人资料"),
        _cE("text", _uM({ class: "menu-arrow" }), "➜")
      ], 8 /* PROPS */, ["onClick"])
    ]),
    _cE("view", _uM({ class: "section" }), [
      _cE("view", _uM({ class: "section-header" }), [
        _cE("text", _uM({ class: "section-title" }), "关于")
      ]),
      _cE("view", _uM({
        class: "menu-item",
        onClick: _ctx.goAgreement
      }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-agreement.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "用户协议"),
        _cE("text", _uM({ class: "menu-arrow" }), "➜")
      ], 8 /* PROPS */, ["onClick"]),
      _cE("view", _uM({
        class: "menu-item",
        onClick: _ctx.goPrivacy
      }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-privacy.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "隐私政策"),
        _cE("text", _uM({ class: "menu-arrow" }), "➜")
      ], 8 /* PROPS */, ["onClick"]),
      _cE("view", _uM({ class: "menu-item" }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-version.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "当前版本"),
        _cE("text", _uM({ class: "menu-value" }), "v1.0.0")
      ])
    ]),
    _cE("view", _uM({ class: "section" }), [
      _cE("view", _uM({
        class: "menu-item",
        onClick: _ctx.clearCache
      }), [
        _cE("image", _uM({
          class: "menu-icon",
          src: "/static/icons/icon-clear.svg",
          mode: "aspectFit"
        })),
        _cE("text", _uM({ class: "menu-text" }), "清除缓存"),
        _cE("text", _uM({ class: "menu-value" }), _tD(_ctx.cacheSize), 1 /* TEXT */)
      ], 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesSettingsSettingsStyles = [_uM([["container", _pS(_uM([["backgroundColor", "#FFF5F8"], ["paddingTop", "20rpx"], ["paddingRight", 0], ["paddingBottom", "20rpx"], ["paddingLeft", 0]]))], ["section", _pS(_uM([["marginTop", "20rpx"], ["marginRight", "24rpx"], ["marginBottom", "20rpx"], ["marginLeft", "24rpx"], ["backgroundColor", "rgba(255,255,255,0.9)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["overflow", "hidden"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"]]))], ["section-header", _pS(_uM([["paddingTop", "30rpx"], ["paddingRight", "36rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "36rpx"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["backgroundColor", "rgba(255,240,245,0.5)"]]))], ["section-title", _pS(_uM([["fontSize", "28rpx"], ["color", "#B8A9C9"]]))], ["menu-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "36rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "36rpx"], ["paddingLeft", "40rpx"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["transitionProperty", "backgroundColor"], ["transitionDuration", "0.2s"], ["backgroundColor:active", "#FFF0F5"], ["borderBottomWidth:last-child", "medium"], ["borderBottomStyle:last-child", "none"], ["borderBottomColor:last-child", "#000000"]]))], ["menu-icon", _pS(_uM([["width", "48rpx"], ["height", "48rpx"], ["marginRight", "24rpx"]]))], ["menu-text", _pS(_uM([["flex", 1], ["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["menu-arrow", _pS(_uM([["fontSize", "34rpx"], ["color", "#B8A9C9"]]))], ["menu-value", _pS(_uM([["fontSize", "28rpx"], ["color", "#B8A9C9"]]))], ["@TRANSITION", _uM([["menu-item", _uM([["property", "backgroundColor"], ["duration", "0.2s"]])]])]])]
