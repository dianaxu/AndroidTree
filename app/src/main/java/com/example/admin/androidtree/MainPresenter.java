package com.example.admin.androidtree;

/**
 * @author Diana
 * @date 2017/7/7
 */

public class MainPresenter {
    private IMainView mIMainView;

    public MainPresenter(IMainView mIMainView) {
        this.mIMainView = mIMainView;
    }

    public void selectFragment(int id) {
        switch (id) {
            case R.id.menu_home:
                mIMainView.showHome();
                break;
            case R.id.menu_setting:
                mIMainView.showSetting();
                break;
            case R.id.menu_knowladge:
                mIMainView.showKnowledge();
                break;
            case R.id.menu_third:
                mIMainView.showThirdLibrary();
                break;
            default:
                break;
        }
    }
}
