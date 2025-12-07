
	import { BASE_URL, DEFAULT_AVATAR_IMAGE } from '@/common/config.uts'
	
	const __sfc__ = defineComponent({
		data() {
			return {
				defaultAvatarImage: DEFAULT_AVATAR_IMAGE,
				userInfo: {
					avatar: '',
					nickname: '',
					phone: ''
				},
				form: {
					nickname: '',
					phone: ''
				},
				avatarFile: null as any
			}
		},
		onLoad() {
			this.loadUserInfo()
		},
		methods: {
			loadUserInfo() {
				const userInfo = uni.getStorageSync('userInfo')
				if (userInfo) {
					this.userInfo = userInfo
					this.form.nickname = userInfo.nickname || ''
					this.form.phone = userInfo.phone || ''
				}
				
				// 从服务器获取最新信息
				const token = uni.getStorageSync('token')
				if (token) {
					uni.request({
						url: `${BASE_URL}/auth/profile`,
						method: 'GET',
						header: { 'Authorization': `Bearer ${token}` },
						success: (res: any) => {
							if (res.data.success && res.data.data.user) {
								const user = res.data.data.user
								this.userInfo = user
								this.form.nickname = user.nickname || ''
								this.form.phone = user.phone || ''
							}
						}
					})
				}
			},
			
			chooseAvatar() {
				uni.chooseImage({
					count: 1,
					sizeType: ['compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						const tempFilePath = res.tempFilePaths[0]
						this.userInfo.avatar = tempFilePath
						this.avatarFile = tempFilePath
					}
				})
			},
			
			handleSubmit() {
				const token = uni.getStorageSync('token')
				
				// 如果有新头像，先上传头像
				if (this.avatarFile) {
					uni.showLoading({ title: '上传头像...' })
					uni.uploadFile({
						url: `${BASE_URL}/auth/upload-avatar`,
						filePath: this.avatarFile,
						name: 'avatar',
						header: { 'Authorization': `Bearer ${token}` },
						success: (uploadRes: any) => {
							const data = JSON.parse(uploadRes.data)
							if (data.success && data.data.avatar) {
								this.userInfo.avatar = data.data.avatar
							}
							this.updateProfile()
						},
						fail: () => {
							uni.hideLoading()
							uni.showToast({ title: '头像上传失败', icon: 'none' })
						}
					})
				} else {
					this.updateProfile()
				}
			},
			
			updateProfile() {
				const token = uni.getStorageSync('token')
				uni.showLoading({ title: '保存中...' })
				
				const requestData = {
					nickname: this.form.nickname,
					phone: this.form.phone
				}
				console.log('Updating profile with data:', requestData)
				
				uni.request({
					url: `${BASE_URL}/auth/profile`,
					method: 'PUT',
					header: {
						'Authorization': `Bearer ${token}`,
						'Content-Type': 'application/json'
					},
					data: requestData,
					success: (res: any) => {
						console.log('Profile update response:', res.data)
						if (res.data.success) {
							// 更新本地存储，使用服务器返回的数据
							const serverUser = res.data.data.user || {}
							const updatedUserInfo = {
								...this.userInfo,
								...serverUser,
								nickname: serverUser.nickname || this.form.nickname,
								phone: serverUser.phone || this.form.phone
							}
							uni.setStorageSync('userInfo', updatedUserInfo)
							
							uni.showToast({ title: '保存成功', icon: 'success' })
							setTimeout(() => {
								uni.navigateBack()
							}, 1500)
						} else {
							uni.showToast({ title: res.data.message || '保存失败', icon: 'none' })
						}
					},
					fail: (err: any) => {
						console.error('Profile update failed:', err)
						uni.showToast({ title: '网络错误', icon: 'none' })
					},
					complete: () => {
						uni.hideLoading()
					}
				})
			}
		}
	})

export default __sfc__
function GenPagesSettingsProfileRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({
      class: "avatar-section",
      onClick: _ctx.chooseAvatar
    }), [
      _cE("view", _uM({ class: "avatar-wrapper" }), [
        _cE("image", _uM({
          class: "avatar",
          src: _ctx.userInfo.avatar || _ctx.defaultAvatarImage,
          mode: "aspectFill"
        }), null, 8 /* PROPS */, ["src"]),
        _cE("view", _uM({ class: "avatar-edit" }), [
          _cE("text", _uM({ class: "edit-text" }), "编辑")
        ])
      ]),
      _cE("text", _uM({ class: "avatar-tip" }), "点击更换头像")
    ], 8 /* PROPS */, ["onClick"]),
    _cE("view", _uM({ class: "form-section" }), [
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "昵称"),
        _cE("input", _uM({
          class: "input",
          type: "text",
          modelValue: _ctx.form.nickname,
          onInput: ($event: UniInputEvent) => {(_ctx.form.nickname) = $event.detail.value},
          placeholder: "请输入昵称",
          "placeholder-class": "placeholder"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "手机号"),
        _cE("input", _uM({
          class: "input",
          type: "number",
          modelValue: _ctx.form.phone,
          onInput: ($event: UniInputEvent) => {(_ctx.form.phone) = $event.detail.value},
          placeholder: "请输入手机号",
          "placeholder-class": "placeholder",
          maxlength: "11"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ])
    ]),
    _cE("view", _uM({
      class: "submit-btn",
      onClick: _ctx.handleSubmit
    }), [
      _cE("text", _uM({ class: "submit-text" }), "保存")
    ], 8 /* PROPS */, ["onClick"])
  ])
}
const GenPagesSettingsProfileStyles = [_uM([["container", _pS(_uM([["backgroundColor", "#FFF5F8"], ["paddingTop", "30rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "24rpx"]]))], ["avatar-section", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["paddingTop", "40rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "40rpx"], ["paddingLeft", "40rpx"], ["marginBottom", "30rpx"]]))], ["avatar-wrapper", _pS(_uM([["position", "relative"], ["width", "180rpx"], ["height", "180rpx"]]))], ["avatar", _pS(_uM([["width", "180rpx"], ["height", "180rpx"], ["borderTopWidth", "6rpx"], ["borderRightWidth", "6rpx"], ["borderBottomWidth", "6rpx"], ["borderLeftWidth", "6rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "rgba(255,255,255,0.8)"], ["borderRightColor", "rgba(255,255,255,0.8)"], ["borderBottomColor", "rgba(255,255,255,0.8)"], ["borderLeftColor", "rgba(255,255,255,0.8)"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.3)"]]))], ["avatar-edit", _pS(_uM([["position", "absolute"], ["bottom", 0], ["left", 0], ["right", 0], ["height", "50rpx"], ["backgroundColor", "rgba(0,0,0,0.5)"], ["borderTopLeftRadius", 0], ["borderTopRightRadius", 0], ["borderBottomRightRadius", "90rpx"], ["borderBottomLeftRadius", "90rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"]]))], ["edit-text", _pS(_uM([["fontSize", "22rpx"], ["color", "#FFFFFF"]]))], ["avatar-tip", _pS(_uM([["marginTop", "20rpx"], ["fontSize", "26rpx"], ["color", "#B8A9C9"]]))], ["form-section", _pS(_uM([["backgroundColor", "rgba(255,255,255,0.9)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["overflow", "hidden"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.1)"], ["backdropFilter", "blur(10px)"]]))], ["form-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "36rpx"], ["paddingRight", "40rpx"], ["paddingBottom", "36rpx"], ["paddingLeft", "40rpx"], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "rgba(255,228,236,0.6)"], ["borderBottomWidth:last-child", "medium"], ["borderBottomStyle:last-child", "none"], ["borderBottomColor:last-child", "#000000"]]))], ["label", _pS(_uM([["width", "140rpx"], ["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["input", _pS(_uM([["flex", 1], ["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["placeholder", _pS(_uM([["color", "#B8A9C9"]]))], ["submit-btn", _pS(_uM([["marginTop", "60rpx"], ["paddingTop", "32rpx"], ["paddingRight", "32rpx"], ["paddingBottom", "32rpx"], ["paddingLeft", "32rpx"], ["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["boxShadow", "0 8rpx 24rpx rgba(255, 105, 180, 0.3)"], ["transform:active", "scale(0.98)"]]))], ["submit-text", _pS(_uM([["fontSize", "32rpx"], ["color", "#FFFFFF"], ["letterSpacing", 2]]))]])]
