<template>
  <v-container class="elevation-12 form__container">
    <div style="background: #262766; padding: 1rem 0">
      <v-img
        :src="require('@/assets/img/logo_ithoot2.png')"
        max-width="150"
        style="margin: 0 auto"
      ></v-img>
    </div>
    <div>
      <h2>Thay đổi mật khẩu</h2>
    </div>
    <v-form>
      <v-text-field
        prepend-icon="lock"
        name="oldPassword"
        label="Mật khẩu cũ"
        type="password"
        v-model="oldPassword"
        required
        v-on:keyup.enter.exact="submit"
        :error-messages="oldPasswordErrors"
        @input="$v.oldPassword.$touch()"
        @blur="$v.oldPassword.$touch()"
      ></v-text-field>
      <v-text-field
        id="password"
        prepend-icon="lock"
        name="password"
        label="Mật khẩu mới"
        type="password"
        v-model="password"
        required
        v-on:keyup.enter.exact="submit"
        :error-messages="passwordErrors"
        @input="$v.password.$touch()"
        @blur="$v.password.$touch()"
      ></v-text-field>
      <v-text-field
        id="confirmPassword"
        prepend-icon="lock"
        name="confirmPassword"
        label="Nhập lại mật khẩu mới"
        type="password"
        v-model="confirmPassword"
        required
        v-on:keyup.enter.exact="submit"
        :error-messages="confirmPasswordErrors"
        @input="$v.confirmPassword.$touch()"
        @blur="$v.confirmPassword.$touch()"
      >
      </v-text-field>
    </v-form>
    <v-card-actions>
      <v-spacer />
      <v-btn color="primary" @click="submit">Thay đổi</v-btn>
    </v-card-actions>
  </v-container>
</template>
<script>
import Footer from "@/components/Footer";
import { validationMixin } from "vuelidate";
import {
  required,
  maxLength,
  minLength,
  email,
  sameAs,
} from "vuelidate/lib/validators";
import StudentAccountService from "../../services/StudentAccountService";

export default {
  mixins: [validationMixin],
  validations: {
    oldPassword: {
      required,
      maxLength: maxLength(20),
      minLength: minLength(5),
    },
    password: { required, maxLength: maxLength(20), minLength: minLength(5) },
    confirmPassword: { sameAsPassword: sameAs("password") },
  },
  components: {
    Footer: Footer,
  },
  computed: {
    oldPasswordErrors() {
      const errors = [];
      if (!this.$v.oldPassword.$dirty) return errors;
      !this.$v.oldPassword.required && errors.push("Điền mật khẩu cũ");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength &&
        errors.push("Mật khẩu không được vượt quá 20 ký tự");
      !this.$v.password.minLength && errors.push("Mật khẩu phải hơn 5 ký tự");
      !this.$v.password.required && errors.push("Điền mật khẩu cũ mới");
      return errors;
    },
    confirmPasswordErrors() {
      const errors = [];
      if (!this.$v.confirmPassword.$dirty) return errors;
      !this.$v.confirmPassword.sameAsPassword &&
        errors.push("Mật khẩu phải trùng nhau");
      return errors;
    },
  },
  data: () => ({
    valid: false,
    oldPassword: "",
    password: "",
    confirmPassword: "",
  }),
  methods: {
    submit() {
      this.$v.$touch();
      if (!this.$v.$anyError) {
        this.updatePassword();
      }
    },
    clear() {
      this.$v.$reset();
      this.oldPassword = "";
      this.password = "";
      this.confirmPassword = "";
    },
    async updatePassword() {
      StudentAccountService.updatePassword({
        oldPassword: this.oldPassword,
        password: this.password,
      })
        .then((response) => {
          this.$eventBus.$emit("nofication", {
            message: "Thay đổi mật khẩu thành công",
          });
          this.clear();
        })
        .catch((error) => error);
    },
  },
};
</script>


<style>
.form__container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}
</style>