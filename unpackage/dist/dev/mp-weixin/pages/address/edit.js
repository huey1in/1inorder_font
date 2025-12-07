"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      isEdit: false,
      id: "",
      form: new UTSJSONObject({
        contact_name: "",
        contact_phone: "",
        address: "",
        detail: "",
        is_default: false
      })
    };
  },
  onLoad(options = null) {
    if (options.id) {
      this.isEdit = true;
      this.id = options.id;
      this.fetchAddressDetail(options.id);
      common_vendor.index.setNavigationBarTitle({ title: "♡ 编辑地址 ♡" });
    } else {
      common_vendor.index.setNavigationBarTitle({ title: "♡ 新增地址 ♡" });
    }
  },
  methods: {
    fetchAddressDetail(id) {
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/addresses/${id}`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          if (res.data.success && res.data.data.address) {
            const addr = res.data.data.address;
            this.form = {
              contact_name: addr.name || addr.contact_name,
              contact_phone: addr.phone || addr.contact_phone,
              // 这里简单处理，将 detail 拆分或者直接显示
              address: addr.detail || addr.address,
              detail: "",
              is_default: !!addr.is_default
            };
          }
        }
      });
    },
    onSwitchChange(e = null) {
      this.form.is_default = e.detail.value;
    },
    saveAddress() {
      if (!this.form.contact_name || !this.form.contact_phone || !this.form.address) {
        common_vendor.index.showToast({ title: "请填写完整信息", icon: "none" });
        return null;
      }
      if (!/^1\d{10}$/.test(this.form.contact_phone)) {
        common_vendor.index.showToast({ title: "手机号格式不正确", icon: "none" });
        return null;
      }
      const token = common_vendor.index.getStorageSync("token");
      const url = this.isEdit ? `${common_config.BASE_URL}/addresses/${this.id}` : `${common_config.BASE_URL}/addresses`;
      const method = this.isEdit ? "PUT" : "POST";
      common_vendor.index.request({
        url,
        method,
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: this.form,
        success: (res = null) => {
          if (res.data.success) {
            common_vendor.index.showToast({ title: "保存成功", icon: "success" });
            setTimeout(() => {
              common_vendor.index.navigateBack();
            }, 1500);
          } else {
            common_vendor.index.showToast({ title: res.data.message || "保存失败", icon: "none" });
          }
        }
      });
    },
    deleteAddress() {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "提示",
        content: "确定要删除这个地址吗？",
        success: (res) => {
          if (res.confirm) {
            const token = common_vendor.index.getStorageSync("token");
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/addresses/${this.id}`,
              method: "DELETE",
              header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
              success: (res2 = null) => {
                if (res2.data.success) {
                  common_vendor.index.showToast({ title: "删除成功", icon: "success" });
                  setTimeout(() => {
                    common_vendor.index.navigateBack();
                  }, 1500);
                }
              }
            });
          }
        }
      }));
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.form.contact_name,
    b: common_vendor.o(($event) => $data.form.contact_name = $event.detail.value),
    c: $data.form.contact_phone,
    d: common_vendor.o(($event) => $data.form.contact_phone = $event.detail.value),
    e: $data.form.address,
    f: common_vendor.o(($event) => $data.form.address = $event.detail.value),
    g: $data.form.detail,
    h: common_vendor.o(($event) => $data.form.detail = $event.detail.value),
    i: $data.form.is_default,
    j: common_vendor.o((...args) => $options.onSwitchChange && $options.onSwitchChange(...args)),
    k: common_vendor.o((...args) => $options.saveAddress && $options.saveAddress(...args)),
    l: $data.isEdit
  }, $data.isEdit ? {
    m: common_vendor.o((...args) => $options.deleteAddress && $options.deleteAddress(...args))
  } : {}, {
    n: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/address/edit.js.map
