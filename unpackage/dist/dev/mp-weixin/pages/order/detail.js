"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      orderId: 0,
      order: {
        items: []
      },
      loading: false
    };
  },
  computed: {
    statusText() {
      var _a, _b;
      const map = {
        "pending": "待确认",
        "confirmed": "已确认",
        "preparing": "制作中",
        "ready": "待取餐",
        "delivering": "配送中",
        "delivered": "已完成",
        "cancelled": "已取消"
      };
      return map[(_a = this.order) === null || _a === void 0 ? null : _a.status] || ((_b = this.order) === null || _b === void 0 ? null : _b.status) || "";
    },
    orderTypeLabel() {
      var _a, _b;
      const map = {
        "delivery": "外卖",
        "pickup": "自取",
        "dine_in": "堂食"
      };
      return map[(_a = this.order) === null || _a === void 0 ? null : _a.order_type] || ((_b = this.order) === null || _b === void 0 ? null : _b.order_type) || "";
    }
  },
  onLoad(options = null) {
    if (options === null || options === void 0 ? null : options.id) {
      this.orderId = Number(options.id);
      this.fetchDetail();
    }
  },
  methods: {
    getProductImage(item = null) {
      if (item.images && item.images.length > 0)
        return item.images[0];
      return DEFAULT_FOOD_IMAGE;
    },
    specString(specs = null) {
      if (!specs)
        return "";
      if (typeof specs === "string")
        return specs;
      return Object.entries(specs).map((_a) => {
        var _b = common_vendor.__read(_a, 2), k = _b[0], v = _b[1];
        return `${k}:${v}`;
      }).join(" ");
    },
    fetchDetail() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      this.loading = true;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/orders/${this.orderId}`,
        method: "GET",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          var _a;
          if (res.data.success && res.data.data) {
            const data = res.data.data;
            this.order = data.order || data;
          } else {
            common_vendor.index.showToast({ title: ((_a = res.data) === null || _a === void 0 ? null : _a.message) || "获取详情失败", icon: "none" });
          }
        },
        fail: () => {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        },
        complete: () => {
          this.loading = false;
        }
      });
    },
    payOrder() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.showLoading({ title: "支付中..." });
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/orders/${this.orderId}/pay`,
        method: "POST",
        header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
        success: (res = null) => {
          var _a, _b;
          if ((_a = res.data) === null || _a === void 0 ? null : _a.success) {
            common_vendor.index.showToast({ title: "支付成功", icon: "success" });
            this.fetchDetail();
          } else {
            common_vendor.index.showToast({ title: ((_b = res.data) === null || _b === void 0 ? null : _b.message) || "支付失败", icon: "none" });
          }
        },
        fail: () => {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        },
        complete: () => {
          common_vendor.index.hideLoading();
        }
      });
    },
    deleteOrder() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.showModal({
        title: "删除订单",
        content: "确定删除该订单吗？",
        success: (res) => {
          if (!res.confirm)
            return null;
          common_vendor.index.request({
            url: `${common_config.BASE_URL}/orders/${this.orderId}`,
            method: "DELETE",
            header: new UTSJSONObject({ "Authorization": `Bearer ${token}` }),
            success: (resp = null) => {
              var _a, _b;
              if ((_a = resp.data) === null || _a === void 0 ? null : _a.success) {
                common_vendor.index.showToast({ title: "已删除", icon: "success" });
                setTimeout(() => {
                  common_vendor.index.switchTab({ url: "/pages/order/list" });
                }, 800);
              } else {
                common_vendor.index.showToast({ title: ((_b = resp.data) === null || _b === void 0 ? null : _b.message) || "删除失败", icon: "none" });
              }
            },
            fail: () => {
              common_vendor.index.showToast({ title: "网络错误", icon: "none" });
            }
          });
        }
      });
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.t($data.order.order_number || "-"),
    b: common_vendor.t($options.statusText),
    c: common_vendor.n(`status-${$data.order.status}`),
    d: common_vendor.t($options.orderTypeLabel),
    e: common_vendor.t($data.order.created_at || "-"),
    f: common_vendor.t($data.order.contact_name || "-"),
    g: common_vendor.t($data.order.contact_phone || "-"),
    h: $data.order.order_type === "delivery"
  }, $data.order.order_type === "delivery" ? {
    i: common_vendor.t($data.order.delivery_address || "-")
  } : {}, {
    j: $data.order.order_type === "pickup" && $data.order.pickup_code
  }, $data.order.order_type === "pickup" && $data.order.pickup_code ? {
    k: common_vendor.t($data.order.pickup_code)
  } : {}, {
    l: common_vendor.f($data.order.items, (item, k0, i0) => {
      return common_vendor.e({
        a: $options.getProductImage(item),
        b: common_vendor.t(item.product_name || "商品"),
        c: item.specs
      }, item.specs ? {
        d: common_vendor.t($options.specString(item.specs))
      } : {}, {
        e: item.notes
      }, item.notes ? {
        f: common_vendor.t(item.notes)
      } : {}, {
        g: common_vendor.t(item.price),
        h: common_vendor.t(item.quantity),
        i: item.id
      });
    }),
    m: common_vendor.t($data.order.total_amount || 0),
    n: $data.order.order_type === "delivery"
  }, $data.order.order_type === "delivery" ? {
    o: common_vendor.t($data.order.delivery_fee || 0)
  } : {}, {
    p: common_vendor.t($data.order.total_amount || 0),
    q: $data.order.notes
  }, $data.order.notes ? {
    r: common_vendor.t($data.order.notes)
  } : {}, {
    s: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/order/detail.js.map
