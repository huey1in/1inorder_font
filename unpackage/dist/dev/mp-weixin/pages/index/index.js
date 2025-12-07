"use strict";
const common_vendor = require("../../common/vendor.js");
const common_config = require("../../common/config.js");
const common_assets = require("../../common/assets.js");
const _sfc_main = common_vendor.defineComponent({
  data() {
    return {
      shop: new UTSJSONObject({
        name: "",
        logo: "",
        description: "",
        opening_hours: "",
        is_open: false,
        announcement: "",
        address: "",
        phone: "",
        min_order_amount: 0,
        delivery_fee: 0,
        delivery_start_amount: 0
      }),
      categories: [],
      products: [],
      currentCategory: 0,
      cartCount: 0,
      cartTotal: 0,
      cartItems: [],
      showCartPopup: false,
      loading: false,
      noMore: false,
      page: 1,
      limit: 10
    };
  },
  onLoad() {
    this.fetchShopInfo();
    this.fetchCategories();
    this.fetchCartCount();
  },
  onShow() {
    this.fetchCartCount();
  },
  methods: {
    // 获取店铺信息
    fetchShopInfo() {
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/shop/info`,
        method: "GET",
        success: (res = null) => {
          if (res.data.success && res.data.data.shop) {
            const info = res.data.data.shop;
            this.shop = Object.assign(Object.assign(Object.assign({}, this.shop), info), { is_open: (info.is_currently_open !== void 0 ? info.is_currently_open : info.is_open) ? true : false });
          }
        },
        fail: (err) => {
          common_vendor.index.__f__("log", "at pages/index/index.uvue:199", "获取店铺信息失败", err);
        }
      });
    },
    // 获取分类列表
    fetchCategories() {
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/categories`,
        method: "GET",
        success: (res = null) => {
          if (res.data.success && res.data.data.categories) {
            this.categories = res.data.data.categories;
            if (this.categories.length > 0) {
              this.fetchProducts();
            }
          }
        },
        fail: (err) => {
          common_vendor.index.__f__("log", "at pages/index/index.uvue:218", "获取分类失败", err);
        }
      });
    },
    // 获取商品列表
    fetchProducts() {
      var _a;
      if (this.loading)
        return null;
      this.loading = true;
      const categoryId = (_a = this.categories[this.currentCategory]) === null || _a === void 0 ? null : _a.id;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/products`,
        method: "GET",
        data: new UTSJSONObject({
          category_id: categoryId,
          page: this.page,
          limit: this.limit
        }),
        success: (res = null) => {
          if (res.data.success && res.data.data.products) {
            const newProducts = res.data.data.products;
            if (this.page === 1) {
              this.products = newProducts;
            } else {
              this.products = [...this.products, ...newProducts];
            }
            this.noMore = newProducts.length < this.limit;
          }
        },
        fail: (err) => {
          common_vendor.index.__f__("log", "at pages/index/index.uvue:249", "获取商品失败", err);
        },
        complete: () => {
          this.loading = false;
        }
      });
    },
    // 获取购物车数量
    fetchCartCount() {
      const token = common_vendor.index.getStorageSync("token");
      if (!token)
        return null;
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/count`,
        method: "GET",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`
        }),
        success: (res = null) => {
          if (res.data.success) {
            this.cartCount = res.data.data.count || 0;
          }
        }
      });
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart`,
        method: "GET",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`
        }),
        success: (res = null) => {
          if (res.data.success && res.data.data.cart) {
            this.cartTotal = res.data.data.cart.total_amount || 0;
            const items = res.data.data.cart.items || [];
            this.cartItems = items.map((item = null) => {
              return new UTSJSONObject({
                id: item.cart_item_id,
                product_id: item.product_id,
                name: item.name,
                price: item.price,
                quantity: item.quantity,
                images: item.images || []
              });
            });
          }
        }
      });
    },
    // 切换分类
    selectCategory(index) {
      this.currentCategory = index;
      this.page = 1;
      this.noMore = false;
      this.products = [];
      this.fetchProducts();
    },
    // 加载更多
    loadMore() {
      if (this.noMore || this.loading)
        return null;
      this.page++;
      this.fetchProducts();
    },
    // 添加到购物车
    addToCart(product = null) {
      const token = common_vendor.index.getStorageSync("token");
      if (!token) {
        common_vendor.index.showToast({ title: "请先登录", icon: "none" });
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return null;
      }
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/add`,
        method: "POST",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          product_id: product.id,
          quantity: 1
        }),
        success: (res = null) => {
          if (res.data.success) {
            common_vendor.index.showToast({ title: "已加入购物车", icon: "success" });
            this.fetchCartCount();
          } else {
            common_vendor.index.showToast({ title: res.data.message || "添加失败", icon: "none" });
          }
        },
        fail: () => {
          common_vendor.index.showToast({ title: "网络错误", icon: "none" });
        }
      });
    },
    // 跳转到商品详情
    goToDetail(id) {
      common_vendor.index.navigateTo({ url: `/pages/product/detail?id=${id}` });
    },
    // 获取商品图片
    getProductImage(product = null) {
      if (product.images && product.images.length > 0) {
        return product.images[0];
      }
      return DEFAULT_FOOD_IMAGE;
    },
    // 获取购物车商品图片
    getCartItemImage(item = null) {
      if (item.images && item.images.length > 0) {
        return item.images[0];
      }
      return DEFAULT_FOOD_IMAGE;
    },
    // 切换购物车弹窗
    toggleCartPopup() {
      this.showCartPopup = !this.showCartPopup;
      if (this.showCartPopup) {
        this.fetchCartCount();
      }
    },
    // 修改购物车商品数量
    changeQuantity(item = null, delta) {
      const newQty = item.quantity + delta;
      if (newQty < 1) {
        this.removeCartItem(item);
        return null;
      }
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/item/${item.id}`,
        method: "PATCH",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        data: new UTSJSONObject({
          quantity: newQty
        }),
        success: (res = null) => {
          if (res.data.success) {
            item.quantity = newQty;
            this.fetchCartCount();
          }
        }
      });
    },
    // 删除购物车商品
    removeCartItem(item = null) {
      const token = common_vendor.index.getStorageSync("token");
      common_vendor.index.request({
        url: `${common_config.BASE_URL}/cart/item/${item.id}`,
        method: "DELETE",
        header: new UTSJSONObject({
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }),
        success: (res = null) => {
          if (res.data.success) {
            const index = this.cartItems.findIndex((i = null) => {
              return i.id === item.id;
            });
            if (index > -1) {
              this.cartItems.splice(index, 1);
            }
            this.fetchCartCount();
            if (this.cartItems.length === 0) {
              this.showCartPopup = false;
            }
          }
        }
      });
    },
    // 清空购物车
    clearCart() {
      common_vendor.index.showModal(new UTSJSONObject({
        title: "确认清空",
        content: "确定要清空购物车吗？",
        success: (res) => {
          if (res.confirm) {
            const token = common_vendor.index.getStorageSync("token");
            common_vendor.index.request({
              url: `${common_config.BASE_URL}/cart/clear`,
              method: "DELETE",
              header: new UTSJSONObject({
                "Authorization": `Bearer ${token}`
              }),
              success: (res2 = null) => {
                if (res2.data.success) {
                  this.cartItems = [];
                  this.cartCount = 0;
                  this.cartTotal = 0;
                  this.showCartPopup = false;
                }
              }
            });
          }
        }
      }));
    },
    // 去结算
    goToCheckout() {
      this.showCartPopup = false;
      common_vendor.index.navigateTo({ url: "/pages/order/create" });
    },
    // 图片加载失败处理
    handleImageError(item = null) {
      if (item.images) {
        item.images = [DEFAULT_FOOD_IMAGE];
      }
    }
  }
});
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return common_vendor.e({
    a: common_vendor.t($data.shop.name),
    b: common_vendor.t($data.shop.description),
    c: common_vendor.t($data.shop.is_open ? "营业中" : "休息中"),
    d: common_vendor.n($data.shop.is_open ? "open" : "closed"),
    e: common_vendor.t($data.shop.opening_hours),
    f: $data.shop.address
  }, $data.shop.address ? {
    g: common_assets._imports_0$1,
    h: common_vendor.t($data.shop.address)
  } : {}, {
    i: $data.shop.phone
  }, $data.shop.phone ? {
    j: common_assets._imports_0,
    k: common_vendor.t($data.shop.phone)
  } : {}, {
    l: common_vendor.t($data.shop.delivery_start_amount || 0),
    m: common_vendor.t($data.shop.delivery_fee || 0),
    n: common_vendor.t($data.shop.min_order_amount || 0),
    o: $data.shop.announcement
  }, $data.shop.announcement ? {
    p: common_assets._imports_2,
    q: common_vendor.t($data.shop.announcement)
  } : {}, {
    r: common_vendor.f($data.categories, (item, index, i0) => {
      return {
        a: common_vendor.t(item.name),
        b: item.id,
        c: common_vendor.n($data.currentCategory === index ? "active" : ""),
        d: common_vendor.o(($event) => $options.selectCategory(index), item.id)
      };
    }),
    s: common_vendor.f($data.products, (product, k0, i0) => {
      return common_vendor.e({
        a: $options.getProductImage(product),
        b: common_vendor.o(($event) => $options.handleImageError(product), product.id),
        c: common_vendor.t(product.name),
        d: common_vendor.t(product.description),
        e: common_vendor.t(product.price),
        f: product.original_price
      }, product.original_price ? {
        g: common_vendor.t(product.original_price)
      } : {}, {
        h: common_vendor.o(($event) => $options.addToCart(product), product.id),
        i: product.id,
        j: common_vendor.o(($event) => $options.goToDetail(product.id), product.id)
      });
    }),
    t: $data.loading
  }, $data.loading ? {} : {}, {
    v: !$data.loading && $data.noMore
  }, !$data.loading && $data.noMore ? {} : {}, {
    w: common_vendor.o((...args) => $options.loadMore && $options.loadMore(...args)),
    x: $data.showCartPopup
  }, $data.showCartPopup ? {
    y: common_vendor.o((...args) => $options.toggleCartPopup && $options.toggleCartPopup(...args))
  } : {}, {
    z: $data.cartCount > 0
  }, $data.cartCount > 0 ? {
    A: common_vendor.o((...args) => $options.clearCart && $options.clearCart(...args)),
    B: common_vendor.f($data.cartItems, (item, k0, i0) => {
      return {
        a: $options.getCartItemImage(item),
        b: common_vendor.o(($event) => $options.handleImageError(item), item.id),
        c: common_vendor.t(item.name),
        d: common_vendor.t(item.price),
        e: common_vendor.o(($event) => $options.changeQuantity(item, -1), item.id),
        f: common_vendor.t(item.quantity),
        g: common_vendor.o(($event) => $options.changeQuantity(item, 1), item.id),
        h: item.id
      };
    }),
    C: common_vendor.n($data.showCartPopup ? "show" : ""),
    D: common_assets._imports_1,
    E: common_vendor.t($data.cartCount),
    F: common_vendor.t($data.cartTotal.toFixed(2)),
    G: common_vendor.o((...args) => $options.toggleCartPopup && $options.toggleCartPopup(...args)),
    H: common_vendor.o((...args) => $options.goToCheckout && $options.goToCheckout(...args)),
    I: common_vendor.n($data.showCartPopup ? "expanded" : "")
  } : {}, {
    J: common_vendor.sei(common_vendor.gei(_ctx, ""), "view")
  });
}
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/index/index.js.map
