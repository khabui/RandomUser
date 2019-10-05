package com.example.randomuser.presenter;

public interface LoginContract {
    interface Presenter {
        void validationLogin(String mail, String password);
    }

    interface View {
        void loginSuccess();

        void loginFail();
    }
}
