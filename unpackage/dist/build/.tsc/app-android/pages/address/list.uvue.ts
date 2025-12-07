
	import { BASE_URL } from '@/common/config.uts'

	const __sfc__ = defineComponent({
		data() {
			return {
				addresses: [] as Array<any>,
				isSelectMode: false
			}
		},
		onLoad(options: any) {
			if (options.select === 'true') {
				this.isSelectMode = true
			}
		},
		onShow() {
			this.fetchAddresses()
		},
		methods: {
			fetchAddresses() {
				const token = uni.getStorageSync('token')
				if (!token) return

				uni.request({
					url: `${BASE_URL}/addresses`,
					method: 'GET',
					header: {
						'Authorization': `Bearer ${token}`
					},
					success: (res: any) => {
						if (res.data.success) {
							const list = res.data.data.addresses || []
							// 适配后端字段
							this.addresses = list.map((item: any) => ({
								...item,
								contact_name: item.name || item.contact_name,
								contact_phone: item.phone || item.contact_phone,
								address: item.address || '',
								detail: item.detail || ''
							}))
						}
					}
				})
			},
			
			addAddress() {
				uni.navigateTo({ url: '/pages/address/edit' })
			},
			
			editAddress(item: any) {
				uni.navigateTo({ url: `/pages/address/edit?id=${item.id}` })
			},
			
			selectAddress(item: any) {
				if (this.isSelectMode) {
					// 如果是选择模式，返回上一页并传递选中的地址
					const pages = getCurrentPages()
					const prevPage = pages[pages.length - 2]
					// @ts-ignore
					if (prevPage.$vm.setAddress) {
						// @ts-ignore
						prevPage.$vm.setAddress(item)
					}
					uni.navigateBack()
				}
			}
		}
	})

export default __sfc__
function GenPagesAddressListRender(this: InstanceType<typeof __sfc__>): any | null {
const _ctx = this
const _cache = this.$.renderCache
  return _cE("view", _uM({ class: "container" }), [
    _ctx.addresses.length > 0
      ? _cE("view", _uM({
          key: 0,
          class: "address-list"
        }), [
          _cE(Fragment, null, RenderHelpers.renderList(_ctx.addresses, (item, index, __index, _cached): any => {
            return _cE("view", _uM({
              class: "address-item",
              key: item.id,
              onClick: () => {_ctx.selectAddress(item)}
            }), [
              _cE("view", _uM({ class: "address-info" }), [
                _cE("view", _uM({ class: "user-row" }), [
                  _cE("text", _uM({ class: "name" }), _tD(item.contact_name), 1 /* TEXT */),
                  _cE("text", _uM({ class: "phone" }), _tD(item.contact_phone), 1 /* TEXT */),
                  isTrue(item.is_default)
                    ? _cE("view", _uM({
                        key: 0,
                        class: "tag"
                      }), [
                        _cE("text", _uM({ class: "tag-text" }), "默认")
                      ])
                    : _cC("v-if", true)
                ]),
                _cE("text", _uM({ class: "address-detail" }), _tD(item.detail || item.address), 1 /* TEXT */)
              ]),
              _cE("view", _uM({
                class: "edit-btn",
                onClick: withModifiers(() => {_ctx.editAddress(item)}, ["stop"])
              }), [
                _cE("image", _uM({
                  class: "edit-icon",
                  src: "/static/icons/icon-edit.svg",
                  mode: "aspectFit"
                }))
              ], 8 /* PROPS */, ["onClick"])
            ], 8 /* PROPS */, ["onClick"])
          }), 128 /* KEYED_FRAGMENT */)
        ])
      : _cE("view", _uM({
          key: 1,
          class: "empty-state"
        }), [
          _cE("image", _uM({
            class: "empty-icon",
            src: "/static/icons/icon-empty.svg",
            mode: "aspectFit"
          })),
          _cE("text", _uM({ class: "empty-text" }), "还没有收货地址哦 ♡")
        ]),
    _cE("view", _uM({ class: "footer-btn" }), [
      _cE("button", _uM({
        class: "add-btn",
        onClick: _ctx.addAddress
      }), [
        _cE("text", _uM({ class: "btn-text" }), "+ 新增收货地址")
      ], 8 /* PROPS */, ["onClick"])
    ])
  ])
}
const GenPagesAddressListStyles = [_uM([["container", _pS(_uM([["backgroundColor", "#FFF5F8"], ["paddingBottom", "120rpx"]]))], ["address-list", _pS(_uM([["paddingTop", "24rpx"], ["paddingRight", "24rpx"], ["paddingBottom", "24rpx"], ["paddingLeft", "24rpx"]]))], ["address-item", _pS(_uM([["backgroundColor", "#FFFFFF"], ["borderTopLeftRadius", "30rpx"], ["borderTopRightRadius", "30rpx"], ["borderBottomRightRadius", "30rpx"], ["borderBottomLeftRadius", "30rpx"], ["paddingTop", "30rpx"], ["paddingRight", "30rpx"], ["paddingBottom", "30rpx"], ["paddingLeft", "30rpx"], ["marginBottom", "24rpx"], ["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["boxShadow", "0 4rpx 16rpx rgba(255, 105, 180, 0.1)"], ["borderTopWidth", 1], ["borderRightWidth", 1], ["borderBottomWidth", 1], ["borderLeftWidth", 1], ["borderTopStyle", "solid"], ["borderRightStyle", "solid"], ["borderBottomStyle", "solid"], ["borderLeftStyle", "solid"], ["borderTopColor", "#FFF0F5"], ["borderRightColor", "#FFF0F5"], ["borderBottomColor", "#FFF0F5"], ["borderLeftColor", "#FFF0F5"]]))], ["address-info", _pS(_uM([["flex", 1]]))], ["user-row", _pS(_uM([["display", "flex"], ["flexDirection", "row"], ["alignItems", "center"], ["marginBottom", "16rpx"]]))], ["name", _pS(_uM([["fontSize", "32rpx"], ["color", "#5D4E6D"], ["marginRight", "20rpx"]]))], ["phone", _pS(_uM([["fontSize", "28rpx"], ["color", "#888888"], ["marginRight", "20rpx"]]))], ["tag", _pS(_uM([["backgroundColor", "#FF69B4"], ["paddingTop", "4rpx"], ["paddingRight", "12rpx"], ["paddingBottom", "4rpx"], ["paddingLeft", "12rpx"], ["borderTopLeftRadius", "10rpx"], ["borderTopRightRadius", "10rpx"], ["borderBottomRightRadius", "10rpx"], ["borderBottomLeftRadius", "10rpx"]]))], ["tag-text", _pS(_uM([["fontSize", "20rpx"], ["color", "#FFFFFF"]]))], ["address-detail", _pS(_uM([["fontSize", "28rpx"], ["color", "#666666"], ["lineHeight", 1.4]]))], ["edit-btn", _pS(_uM([["paddingTop", "20rpx"], ["paddingRight", "20rpx"], ["paddingBottom", "20rpx"], ["paddingLeft", "20rpx"]]))], ["edit-icon", _pS(_uM([["width", "40rpx"], ["height", "40rpx"]]))], ["empty-state", _pS(_uM([["display", "flex"], ["flexDirection", "column"], ["alignItems", "center"], ["justifyContent", "center"], ["paddingTop", "200rpx"]]))], ["empty-icon", _pS(_uM([["width", "200rpx"], ["height", "200rpx"], ["marginBottom", "30rpx"], ["opacity", 0.5]]))], ["empty-text", _pS(_uM([["fontSize", "28rpx"], ["color", "#B8A9C9"]]))], ["footer-btn", _pS(_uM([["position", "fixed"], ["bottom", 0], ["left", 0], ["right", 0], ["paddingTop", "20rpx"], ["paddingRight", "30rpx"], ["paddingBottom", calc(20rpx+env(safe-area-inset-bottom))], ["paddingLeft", "30rpx"], ["backgroundColor", "rgba(255,255,255,0.9)"], ["backdropFilter", "blur(10px)"]]))], ["add-btn", _pS(_uM([["backgroundImage", "linear-gradient(135deg, #FFB6C1 0%, #FF69B4 100%)"], ["backgroundColor", "rgba(0,0,0,0)"], ["borderTopLeftRadius", "40rpx"], ["borderTopRightRadius", "40rpx"], ["borderBottomRightRadius", "40rpx"], ["borderBottomLeftRadius", "40rpx"], ["height", "88rpx"], ["display", "flex"], ["justifyContent", "center"], ["alignItems", "center"], ["borderTopWidth", "medium"], ["borderRightWidth", "medium"], ["borderBottomWidth", "medium"], ["borderLeftWidth", "medium"], ["borderTopStyle", "none"], ["borderRightStyle", "none"], ["borderBottomStyle", "none"], ["borderLeftStyle", "none"], ["borderTopColor", "#000000"], ["borderRightColor", "#000000"], ["borderBottomColor", "#000000"], ["borderLeftColor", "#000000"], ["boxShadow", "0 8rpx 20rpx rgba(255, 105, 180, 0.3)"], ["transform:active", "scale(0.98)"]]))], ["btn-text", _pS(_uM([["color", "#FFFFFF"], ["fontSize", "32rpx"], ["fontWeight", "bold"], ["letterSpacing", 1]]))]])]
