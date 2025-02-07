<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item :label="$t('profile.oldPassword')" prop="oldPassword">
      <el-input v-model="user.oldPassword" :placeholder="$t('profile.enterOldPassword')" type="password" show-password/>
    </el-form-item>
    <el-form-item :label="$t('profile.newPassword')" prop="newPassword">
      <el-input v-model="user.newPassword" :placeholder="$t('profile.enterNewPassword')" type="password" show-password/>
    </el-form-item>
    <el-form-item :label="$t('profile.confirmPassword')" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" :placeholder="$t('profile.enterConfirmPassword')" type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('profile.save') }}</el-button>
      <el-button type="danger" size="mini" @click="close">{{ $t('profile.close') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error(this.$t('profile.passwordMismatch')));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: this.$t('profile.oldPasswordRequired'), trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: this.$t('profile.newPasswordRequired'), trigger: "blur" },
          { min: 6, max: 20, message: this.$t('profile.passwordLength'), trigger: "blur" },
          { pattern: /^[^<>"'|\\]+$/, message: this.$t('profile.passwordInvalid'), trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: this.$t('profile.confirmPasswordRequired'), trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess(this.$t('profile.editSuccess'));
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
