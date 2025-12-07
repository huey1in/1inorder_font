"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      defaultAvatarImage: "/static/icons/default-avatar.png",
      userInfo: new UTSJSONObject({
        avatar: "",
        nickname: "",
        phone: ""
      }),
      form: new UTSJSONObject({
        nickname: "",
        phone: ""
      }),
      avatarFile: null
    };
  },
  onLoad() {
    this.loadUserInfo();
  },
  methods: {
    loadUserInfo() {
      const userInfo = common_vendor.index.getStorageSync("userInfo");
      if (userInfo) {
        this.userInfo = userInfo;
        this.form.nickname = userInfo.nickname || "";
        this.form.phone = userInfo.phone || "";
      }
      const token = common_vendor.index.getStorageSync("token");
      if (token) {
        common_vendor.index.request({
          url: `${common_config.BASE_URL}/auth/profile`,
          method: "GET",
          header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
          success: (res = null) => {
            if (res.data.success && res.data.data.user) {
              const user = res.data.data.user;
              this.userInfo = user;
              this.form.nickname = user.nickname || "";
              this.form.phone = user.phone || "";
            }
          }
        });
      }
    },
    chooseAvatar() {
      common_vendor.index.chooseImage(new UTSJSONObject({
        count: 1,
        sizeType: ["compressed"],
        sourceType: ["album", "camera"],
        success: (res) => {
          const tempFilePath = res.tempFilePaths[0];
          this.userInfo.avatar = tempFilePath;
          this.avatarFile = tempFilePath;
        }
      }));
    },
    handleSubmit() {
      const token = common_vendor.index.getStorageSync("token");
      if (this.avatarFile) {
        common_vendor.index.showLoading({ title: "上传头像..." });
        common_vendor.index.uploadFile({
          url: `${common_config.BASE_URL}/auth/upload-avatar`,
          filePath: this.avatarFile,
          name: "avatar",
          header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
          success: (uploadRes = null) => {
            const data = UTS.JSON.parse(uploadRes.data);
            if (data.success && data.data.avatar) {
              this.userInfo.avatar = data.data.avatar;
            }
            this.updateProfile();
          },
          fail: () => {
            common_vendor.index.hideLoading();
            common_vendor.index.showToast({ title: "头像上传失败", icon: "none" });
          }
        });
      } else {
        this.updateProfile();
      }
    },
    updateProfile() {
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.showLoading({ title: "保存中..." });
      const requestData = new UTSJSONObject({
        nickname: this.form.nickname,
        phone: this.form.phone
      });
      common_vendor.index.__f__("log", "at pages/settings/profile.uvue:144", "Updating profile with data:", requestData);
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/auth/profile`,
        method: "PUT",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: requestData,
        success: (res = null) => {
          common_vendor.index.__f__("log", "at pages/settings/profile.uvue:155", "Profile update response:", res.data);
          if (res.data.success) {
            const serverUser = res.data.data.user || new UTSJSONObject({});
            const updatedUserInfo = new UTSJSONObject(Object.assign(Object.assign(Object.assign({}, this.userInfo), serverUser), { nickname: serverUser.nickname || this.form.nickname, phone: serverUser.phone || this.form.phone }));
            common_vendor.index.setStorageSync("userInfo", updatedUserInfo);
            common_vendor.index.showToast({ title: "保存成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.navigateBack();
            }, 1500);
          } else {
            common_vendor.index.showToast({ title: res.data.message || "保存失败", icon: "none" });
          }
        },
        fail: (err = null) => {
          common_vendor.index.__f__("error", "at pages/settings/profile.uvue:176", "Profile update failed:", err);
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        },
        complete: () => {
          common_vendor.index.hideLoading();
        }
      });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: $data.userInfo.avatar || $data.defaultAvatarImage,
    b: common_vendor.o((...args) => $options.chooseAvatar && $options.chooseAvatar(...args)),
    c: $data.form.nickname,
    d: common_vendor.o(($event) => $data.form.nickname = $event.detail.value),
    e: $data.form.phone,
    f: common_vendor.o(($event) => $data.form.phone = $event.detail.value),
    g: common_vendor.o((...args) => $options.handleSubmit && $options.handleSubmit(...args)),
    h: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/settings/profile.js.map
