
	import { BASE_URL } from '@/common/config.uts'

	const __sfc__ = defineComponent({
		data() {
			return {
				isEdit: false,
				id: '',
				form: {
					contact_name: '',
					contact_phone: '',
					address: '',
					detail: '',
					is_default: false
				}
			}
		},
		onLoad(options: any) {
			if (options.id) {
				this.isEdit = true
				this.id = options.id
				this.fetchAddressDetail(options.id)
				uni.setNavigationBarTitle({ title: '♡ 编辑地址 ♡' })
			} else {
				uni.setNavigationBarTitle({ title: '♡ 新增地址 ♡' })
			}
		},
		methods: {
			fetchAddressDetail(id: string) {
				const token = uni.getStorageSync('token')
				uni.request({
					url: `${BASE_URL}/addresses/${id}`,
					method: 'GET',
					header: { 'Authorization': `Bearer ${token}` },
					success: (res: any) => {
						if (res.data.success && res.data.data.address) {
							const addr = res.data.data.address
							// 适配后端返回的字段 name/phone/detail
							this.form = {
								contact_name: addr.name || addr.contact_name,
								contact_phone: addr.phone || addr.contact_phone,
								// 这里简单处理，将 detail 拆分或者直接显示
								address: addr.detail || addr.address, 
								detail: '', // 既然 detail 已经包含了完整地址，这里可以留空，或者尝试拆分
								is_default: !!addr.is_default
							}
						}
					}
				})
			},

			onSwitchChange(e: any) {
				this.form.is_default = e.detail.value
			},

			saveAddress() {
				if (!this.form.contact_name || !this.form.contact_phone || !this.form.address) {
					uni.showToast({ title: '请填写完整信息', icon: 'none' })
					return
				}
				if (!/^1\d{10}$/.test(this.form.contact_phone)) {
					uni.showToast({ title: '手机号格式不正确', icon: 'none' })
					return
				}

				const token = uni.getStorageSync('token')
				const url = this.isEdit ? `${BASE_URL}/addresses/${this.id}` : `${BASE_URL}/addresses`
				const method = this.isEdit ? 'PUT' : 'POST'

				uni.request({
					url: url,
					method: method,
					header: {
						'Authorization': `Bearer ${token}`,
						'Content-Type': 'application/json'
					},
					data: this.form,
					success: (res: any) => {
						if (res.data.success) {
							uni.showToast({ title: '保存成功', icon: 'success' })
							setTimeout(() => {
								uni.navigateBack()
							}, 1500)
						} else {
							uni.showToast({ title: res.data.message || '保存失败', icon: 'none' })
						}
					}
				})
			},

			deleteAddress() {
				uni.showModal({
					title: '提示',
					content: '确定要删除这个地址吗？',
					success: (res) => {
						if (res.confirm) {
							const token = uni.getStorageSync('token')
							uni.request({
								url: `${BASE_URL}/addresses/${this.id}`,
								method: 'DELETE',
								header: { 'Authorization': `Bearer ${token}` },
								success: (res: any) => {
									if (res.data.success) {
										uni.showToast({ title: '删除成功', icon: 'success' })
										setTimeout(() => {
											uni.navigateBack()
										}, 1500)
									}
								}
							})
						}
					}
				})
			}
		}
	})

export default __sfc__
function GenPagesAddressEditRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
const _component_switch = resolveComponent("switch")

  return _cE("view", _uM({ class: "container" }), [
    _cE("view", _uM({ class: "form-card" }), [
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "联系人"),
        _cE("input", _uM({
          class: "input",
          type: "text",
          modelValue: _ctx.form.contact_name,
          onInput: ($event: UniInputEvent) => {(_ctx.form.contact_name) = $event.detail.value},
          placeholder: "请填写收货人姓名",
          "placeholder-class": "placeholder"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "手机号"),
        _cE("input", _uM({
          class: "input",
          type: "number",
          modelValue: _ctx.form.contact_phone,
          onInput: ($event: UniInputEvent) => {(_ctx.form.contact_phone) = $event.detail.value},
          placeholder: "请填写收货人手机号",
          "placeholder-class": "placeholder",
          maxlength: "11"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "收货地址"),
        _cE("input", _uM({
          class: "input",
          type: "text",
          modelValue: _ctx.form.address,
          onInput: ($event: UniInputEvent) => {(_ctx.form.address) = $event.detail.value},
          placeholder: "小区/写字楼/学校等",
          "placeholder-class": "placeholder"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "form-item" }), [
        _cE("text", _uM({ class: "label" }), "门牌号"),
        _cE("input", _uM({
          class: "input",
          type: "text",
          modelValue: _ctx.form.detail,
          onInput: ($event: UniInputEvent) => {(_ctx.form.detail) = $event.detail.value},
          placeholder: "例：8号楼808室",
          "placeholder-class": "placeholder"
        }), null, 40 /* PROPS, NEED_HYDRATION */, ["modelValue", "onInput"])
      ]),
      _cE("view", _uM({ class: "form-item switch-item" }), [
        _cE("text", _uM({ class: "label" }), "设为默认地址"),
        _cV(_component_switch, _uM({
          checked: _ctx.form.is_default,
          color: "#FF69B4",
          onChange: _ctx.onSwitchChange
        }), null, 8 /* PROPS */, ["checked", "onChange"])
      ])
    ]),
    _cE("view", _uM({ class: "footer-btns" }), [
      _cE("button", _uM({
        class: "save-btn",
        onClick: _ctx.saveAddress
      }), [
        _cE("text", _uM({ class: "save-text" }), "保存地址")
      ], 8 /* PROPS */, ["onClick"]),
      isTrue(_ctx.isEdit)
        ? _cE("button", _uM({
            key: 0,
            class: "delete-btn",
            onClick: _ctx.deleteAddress
          }), [
            _cE("text", _uM({ class: "delete-text" }), "删除地址")
          ], 8 /* PROPS */, ["onClick"])
        : _cC("v-if", true)
    ])
  ])
}
const GenPagesAddressEditStyles = [_uM([["container", _pS(_uM([["backgroundColor", "#FFF5F8"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"]]))], ["form-card", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["paddingTop", 0], ["paddingRight", "30rpx"], ["paddingBottom", 0], ["paddingLeft", "30rpx"], ["boxShadow", "0 4rpx 16rpx rgba(255, 105, 180, 0.1)"]]))], ["form-item", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["paddingTop", "36rpx"], ["paddingRight", 0], ["paddingBottom", "36rpx"], ["paddingLeft", 0], ["borderBottomWidth", 1], ["borderBottomStyle", "solid"], ["borderBottomColor", "#FFF0F5"], ["borderBottomWidth:last-child", "medium"], ["borderBottomStyle:last-child", "none"], ["borderBottomColor:last-child", "#000000"]]))], ["label", _pS(_uM([["width", "180rpx"], ["fontSize", "30rpx"], ["color", "#5D4E6D"]]))], ["input", _pS(_uM([["flex", 1], ["fontSize", "30rpx"], ["color", "#333333"]]))], ["placeholder", _pS(_uM([["color", "#CCCCCC"]]))], ["switch-item", _pS(_uM([["justifyContent", "space-between"]]))], ["footer-btns", _pS(_uM([["marginTop", "60rpx"]]))], ["save-btn", _pS(_uM([["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["height", "88rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopWidth", "medium"], ["borderRightWidth", "medium"], ["borderBottomWidth", "medium"], ["borderLeftWidth", "medium"], ["borderTopStyle", "none"], ["borderRightStyle", "none"], ["borderBottomStyle", "none"], ["borderLeftStyle", "none"], ["borderTopColor", "#000000"], ["borderRightColor", "#000000"], ["borderBottomColor", "#000000"], ["borderLeftColor", "#000000"], ["boxShadow", "0 8rpx 20rpx rgba(255, 105, 180, 0.3)"], ["marginBottom", "30rpx"], ["transform:active", "scale(0.98)"]]))], ["save-text", _pS(_uM([["color", "#FFFFFF"], ["fontSize", "32rpx"], ["fontWeight", "bold"], ["letterSpacing", 1]]))], ["delete-btn", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["height", "88rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopWidth", "2rpx"], ["borderRightWidth", "2rpx"], ["borderBottomWidth", "2rpx"], ["borderLeftWidth", "2rpx"], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFB6C1"], ["borderRightColor", "#FFB6C1"], ["borderBottomColor", "#FFB6C1"], ["borderLeftColor", "#FFB6C1"], ["backgroundColor:active", "#FFF0F5"]]))], ["delete-text", _pS(_uM([["color", "#FF69B4"], ["fontSize", "32rpx"], ["fontWeight", "bold"]]))]])]
