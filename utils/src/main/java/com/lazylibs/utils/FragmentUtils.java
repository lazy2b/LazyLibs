package com.lazylibs.utils;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public interface FragmentUtils {


    /**
     * showFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void showFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment) {
        showFragmentToActivity(containerId, fragmentManager, fragment, null, 0);
    }

    /**
     * showFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    static void showFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, String tag) {
        showFragmentToActivity(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * showFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param styleRes        transitionStyle资源ID
     */
    static void showFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, @StyleRes int styleRes) {
        showFragmentToActivity(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * showFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void showFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, String tag, @StyleRes int styleRes) {
        if (!fragment.isAdded()) {
            addFragmentToActivity(containerId, fragmentManager, fragment, tag, styleRes);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment);
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * hideFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void hideFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment) {
        hideFragmentToActivity(containerId, fragmentManager, fragment, null, 0);
    }

    /**
     * hideFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    static void hideFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, String tag) {
        hideFragmentToActivity(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * hideFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param styleRes        transitionStyle资源ID
     */
    static void hideFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, @StyleRes int styleRes) {
        hideFragmentToActivity(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * hideFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void hideFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment, String tag, @StyleRes int styleRes) {
        if (!fragment.isAdded()) {
            addFragmentToActivity(containerId, fragmentManager, fragment, tag, styleRes);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment);
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * addFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment) {
        addFragmentToActivity(containerId, fragmentManager, fragment, null, 0);
    }

    /**
     * addFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment, String tag) {
        addFragmentToActivity(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * addFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param styleRes        transitionStyle资源ID
     */
    static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment, @StyleRes int styleRes) {
        addFragmentToActivity(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * addFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment, String tag, @StyleRes int styleRes) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (tag == null) {
            ft.add(containerId, fragment);
        } else {
            ft.add(containerId, fragment, tag);
        }
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commit();
    }

    /**
     * addFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void addFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                      Fragment fragment) {
        addFragmentToActivityBackStack(containerId, fragmentManager, fragment, null);
    }

    /**
     * addFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    static void addFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                      Fragment fragment, @Nullable String tag) {
        addFragmentToActivityBackStack(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * addFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param styleRes        transitionStyle资源ID
     */
    static void addFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                      Fragment fragment, @StyleRes int styleRes) {
        addFragmentToActivityBackStack(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * addFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void addFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                      Fragment fragment, @Nullable String tag, @StyleRes int styleRes) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, tag);
        ft.addToBackStack(tag);
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commit();
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment) {
        replaceFragmentToActivity(containerId, fragmentManager, fragment, null, 0);
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment, @Nullable String tag) {
        replaceFragmentToActivity(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param styleRes        transitionStyle资源ID
     */
    static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment, @StyleRes int styleRes) {
        replaceFragmentToActivity(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment, @Nullable String tag, @StyleRes int styleRes) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (tag == null) {
            ft.replace(containerId, fragment);
        } else {
            ft.replace(containerId, fragment, tag);
        }
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commit();
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    static void replaceFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                          Fragment fragment) {
        replaceFragmentToActivity(containerId, fragmentManager, fragment, null, 0);
    }

    /**
     * replaceFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例标记
     * @param tag             标记
     */
    static void replaceFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                          Fragment fragment, @Nullable String tag) {
        replaceFragmentToActivityBackStack(containerId, fragmentManager, fragment, tag, 0);
    }

    /**
     * replaceFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例标记
     * @param styleRes        transitionStyle资源ID
     */
    static void replaceFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                          Fragment fragment, @StyleRes int styleRes) {
        replaceFragmentToActivityBackStack(containerId, fragmentManager, fragment, null, styleRes);
    }

    /**
     * replaceFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例标记
     * @param tag             标记
     * @param styleRes        transitionStyle资源ID
     */
    static void replaceFragmentToActivityBackStack(@IdRes int containerId, FragmentManager fragmentManager,
                                                          Fragment fragment, @Nullable String tag, @StyleRes int styleRes) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, fragment, tag);
        ft.addToBackStack(tag);
        if (styleRes != 0) {
            ft.setTransitionStyle(styleRes);
        }
        ft.commit();
    }

    /**
     * 退出当前FragmentBackStack的一个实例
     *
     * @param fragmentManager Fragment管理器
     * @return true : success  false: fail
     */
    static boolean popBackStack(FragmentManager fragmentManager) {
        boolean isBackSuccess = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    /**
     * 退出当前FragmentBackStack一个指tag的实例
     *
     * @param tag             标记
     * @param fragmentManager Fragment管理器
     * @return true : success  false: fail
     */
    static boolean popBackStack(String tag, FragmentManager fragmentManager) {
        boolean isBackSuccess = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    /**
     * 退出当前所有FragmentBackStack
     *
     * @param fragmentManager Fragment管理器
     */
    static void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
