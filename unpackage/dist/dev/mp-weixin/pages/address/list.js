"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      addresses: [],
      isSelectMode: false
    };
  },
  onLoad(options = null) {
    if (options.select === "true") {
      this.isSelectMode = true;
    }
  },
  onShow() {
    this.fetchAddresses();
  },
  methods: {
    fetchAddresses() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token)
        return null;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/addresses`,
        method: "GET",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`
        }),
        success: (res = null) => {
          if (res.data.success) {
            const list = res.data.data.addresses || [];
            this.addresses = list.map((item = null) => {
              return new UTSJSONObject(Object.assign(Object.assign({}, item), { contact_name: item.name || item.contact_name, contact_phone: item.phone || item.contact_phone, address: item.address || "", detail: item.detail || "" }));
            });
          }
        }
      });
    },
    addAddress() {
      common_vendor.index.navigateTo({ url: "/pages/address/edit" });
    },
    editAddress(item = null) {
      common_vendor.index.navigateTo({ url: `/pages/address/edit?id=${item.id}` });
    },
    selectAddress(item = null) {
      if (this.isSelectMode) {
        const pages = getCurrentPages();
        const prevPage = pages[pages.length - 2];
        if (prevPage.$vm.setAddress) {
          prevPage.$vm.setAddress(item);
        }
        common_vendor.index.navigateBack();
      }
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: $data.addresses.length > 0
  }, $data.addresses.length > 0 ? {
    b: common_vendor.f($data.addresses, (item, index, i0) => {
      return common_vendor.e({
        a: common_vendor.t(item.contact_name),
        b: common_vendor.t(item.contact_phone),
        c: item.is_default
      }, item.is_default ? {} : {}, {
        d: common_vendor.t(item.detail || item.address),
        e: common_vendor.o(($event) => $options.editAddress(item), item.id),
        f: item.id,
        g: common_vendor.o(($event) => $options.selectAddress(item), item.id)
      });
    }),
    c: common_assets._imports_0$4
  } : {
    d: common_assets._imports_1$4
  }, {
    e: common_vendor.o((...args) => $options.addAddress && $options.addAddress(...args)),
    f: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/address/list.js.map
