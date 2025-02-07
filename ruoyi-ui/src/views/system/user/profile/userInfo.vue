<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item :label="$t('profile.nickName')" prop="nickName">
      <el-input v-model="form.nickName" maxlength="30" />
    </el-form-item> 
    <el-form-item :label="$t('profile.phoneNumber')" prop="phonenumber">
      <el-input v-model="form.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item :label="$t('profile.email')" prop="email">
      <el-input v-model="form.email" maxlength="50" />
    </el-form-item>
    <el-form-item :label="$t('profile.gender')">
      <el-radio-group v-model="form.sex">
        <el-radio label="0">{{ $t('profile.male') }}</el-radio>
        <el-radio label="1">{{ $t('profile.female') }}</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">{{ $t('profile.save') }}</el-button>
      <el-button type="danger" size="mini" @click="close">{{ $t('profile.close') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      form: {},
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: this.$t('profile.nickNameRequired'), trigger: "blur" }
        ],
        email: [
          { required: true, message: this.$t('profile.emailRequired'), trigger: "blur" },
          {
            type: "email",
            message: this.$t('profile.emailInvalid'),
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: this.$t('profile.phoneNumberRequired'), trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: this.$t('profile.phoneNumberInvalid'),
            trigger: "blur"
          }
        ]
      }
    };
  },
  watch: {
    user: {
      handler(user) {
        if (user) {
          this.form = { nickName: user.nickName, phonenumber: user.phonenumber, email: user.email, sex: user.sex };
        }
      },
      immediate: true
    }
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.form).then(response => {
            this.$modal.msgSuccess(this.$t('profile.editSuccess'));
            this.user.phonenumber = this.form.phonenumber;
            this.user.email = this.form.email;
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
