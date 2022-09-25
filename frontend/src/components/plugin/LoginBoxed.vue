<template>
  <div
    class="login-bg"
    style="
      height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    "
  >
    <v-container>
      <v-row align-content="center" justify="center">
        <v-col cols="12" sm="10">
          <v-card class="elevation-6">
            <v-window>
              <v-window-item>
                <v-row>
                  <v-col cols="12" md="6">
                    <v-card-text class="">
                      <v-card-title align-content="center">
                        <v-img
                          :src="require('@/assets/img/logo_ithoot.png')"
                          max-width="100"
                        ></v-img>
                      </v-card-title>
                      <h1 class="text-center">Đăng Nhập</h1>
                      <v-row
                        align-content="center"
                        justify="center"
                        class="mb-5"
                      >
                        <v-col cols="12" sm="8">
                          <v-text-field
                            label="Tài khoản"
                            outlined
                            dense
                            color="#262766"
                            autocomplete="false"
                            class="mt-16"
                            name="username"
                            id="txtUsername"
                            v-model="username"
                            required
                            v-on:keyup.enter.exact="submit"
                            :error-messages="usernameErrors"
                            @input="$v.username.$touch()"
                            @blur="$v.username.$touch()"
                          />
                          <v-text-field
                            label="Mật Khẩu"
                            outlined
                            dense
                            color="#262766"
                            autocomplete="false"
                            type="password"
                            id="txtPassword"
                            v-model="password"
                            required
                            v-on:keyup.enter.exact="submit"
                            :error-messages="passwordErrors"
                            @input="$v.password.$touch()"
                            @blur="$v.password.$touch()"
                          />

                          <v-btn
                            color="#262766"
                            dark
                            block
                            @click="submit"
                            rounded
                            id="btnLogin"
                          >
                            Đăng Nhập
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-col>
                  <v-col
                    cols="12"
                    md="6"
                    style="background: #262766"
                    class="rounded-bl-xl login__banner"
                  >
                    <div style="text-align: center; padding: 200px 0">
                      <v-card-text class="white--text">
                        <h1 class="text-center">IThoot</h1>
                        <br />
                        <h3 class="text-center">Made by Bob Vu</h3>
                      </v-card-text>
                    </div>
                  </v-col>
                </v-row>
              </v-window-item>
            </v-window>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import AuthenticationService from "@/services/AuthenticationService";
import { validationMixin } from "vuelidate";
import { required, maxLength, minLength } from "vuelidate/lib/validators";
export default {
  props: {
    source: String,
  },
  mixins: [validationMixin],

  validations: {
    username: { required, maxLength: maxLength(30), minLength: minLength(5) },
    password: { required, maxLength: maxLength(30), minLength: minLength(5) },
  },
  computed: {
    usernameErrors() {
      const errors = [];
      if (!this.$v.username.$dirty) return errors;
      !this.$v.username.maxLength && errors.push("Tài khoản tối đa 20 ký tự");
      !this.$v.username.minLength && errors.push("Tài khoản ít nhất 5 ký tự");
      !this.$v.username.required &&
        errors.push("Tài khoản không được để trống");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength && errors.push("Mật khẩu tối đa 20 ký tự");
      !this.$v.password.minLength && errors.push("Mật khẩu ít nhất 5 ký tự");
      !this.$v.password.required && errors.push("Mật khẩu không được để trống");
      return errors;
    },
  },
  data: () => ({
    valid: false,
    username: "",
    password: "",
  }),
  created() {
    this.$store.commit("setToken", null);
    this.$store.commit("setUsername", null);
    this.$store.commit("setRoles", null);
  },
  methods: {
    submit() {
      this.$v.$touch();
      // console.log(config.backendUrl);
      if (!this.$v.$anyError) {
        this.login();
      }
    },
    clear() {
      this.$v.$reset();
      this.username = "";
      this.password = "";
    },
    login() {
      AuthenticationService.login({
        username: this.username,
        password: this.password,
      }).then((response) => {
        console.log(response);
        this.$store.commit("setToken", "Bearer " + response.data.jwt);
        this.$store.commit("setUsername", response.data.username);
        this.$store.commit("setRoles", response.data.authorities);

        this.$router.push({
          name: response.data.authorities[0].authority,
        });
      });
    },
  },
};
</script>
<style scoped>
.v-application .rounded-bl-xl {
  border-bottom-left-radius: 300px !important;
}
.v-application .rounded-br-xl {
  border-bottom-right-radius: 300px !important;
}

@media screen and (max-width: 975px) {
  .login__banner {
    display: none;
  }
  .login-bg {
    background-color: #262766;
  }
}
</style>