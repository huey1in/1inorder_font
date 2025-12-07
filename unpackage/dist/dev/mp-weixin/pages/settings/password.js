"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      form: new UTSJSONObject({
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      })
    };
  },
  methods: {
    handleSubmit() {
      if (!this.form.oldPassword) {
        common_vendor.index.showToast({ title: "请输入当前密码", icon: "none" });
        return null;
      }
      if (!this.form.newPassword) {
        common_vendor.index.showToast({ title: "请输入新密码", icon: "none" });
        return null;
      }
      if (this.form.newPassword.length < 6 || this.form.newPassword.length > 20) {
        common_vendor.index.showToast({ title: "密码长度为6-20位", icon: "none" });
        return null;
      }
      if (this.form.newPassword !== this.form.confirmPassword) {
        common_vendor.index.showToast({ title: "两次密码输入不一致", icon: "none" });
        return null;
      }
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.showLoading({ title: "提交中..." });
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/auth/change-password`,
        method: "POST",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          currentPassword: this.form.oldPassword,
          newPassword: this.form.newPassword
        }),
        success: (res = null) => {
          if (res.data.success) {
            common_vendor.index.showToast({ title: "密码修改成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.navigateBack();
            }, 1500);
          } else {
            common_vendor.index.showToast({ title: res.data.message || "修改失败", icon: "none" });
          }
        },
        fail: () => {
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
    a: $data.form.oldPassword,
    b: common_vendor.o(($event) => $data.form.oldPassword = $event.detail.value),
    c: $data.form.newPassword,
    d: common_vendor.o(($event) => $data.form.newPassword = $event.detail.value),
    e: $data.form.confirmPassword,
    f: common_vendor.o(($event) => $data.form.confirmPassword = $event.detail.value),
    g: common_vendor.o((...args) => $options.handleSubmit && $options.handleSubmit(...args)),
    h: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  };
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/settings/password.js.map
