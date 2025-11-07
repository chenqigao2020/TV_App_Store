package com.app.base;

/**
 * 适应ViewPager的Fragment，
 * 他将在可见的时候回调onViewPagerFragmentResume，
 * 不可见的时候回调onViewPagerFragmentPause
 * **/
public class ViewPagerFragment extends BaseFragment {

    private boolean isOnViewPagerFragmentResumed = false;

    private boolean isOnViewPagerFragmentPaused = false;

    //显示
    protected void onViewPagerFragmentResume(){

    }

    //隐藏
    protected void onViewPagerFragmentPause(){

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            notifyResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        notifyPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isResumed()){
            if(isVisibleToUser){
                notifyResume();
            }else{
                notifyPause();
            }
        }
    }

    private void notifyResume(){
        if(!isOnViewPagerFragmentResumed){
            isOnViewPagerFragmentResumed = true;
            isOnViewPagerFragmentPaused = false;
            onViewPagerFragmentResume();
        }
    }

    private void notifyPause(){
        if(isOnViewPagerFragmentResumed && !isOnViewPagerFragmentPaused){
            isOnViewPagerFragmentPaused = true;
            isOnViewPagerFragmentResumed = false;
            onViewPagerFragmentPause();
        }
    }

}
