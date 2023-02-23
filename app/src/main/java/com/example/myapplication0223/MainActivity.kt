package com.example.myapplication0223

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val MIN_SCALE = 0.8f
    private val MIN_ALPHA = 0.8f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager_ljh : ViewPager2 = findViewById(R.id.viewPager_ljh)

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        viewPager_ljh.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        viewPager_ljh.offscreenPageLimit = 1 //몇개의 페이지를 미리 로드 해둘것인지
        viewPager_ljh.adapter = ViewPagerAdapter(getLjhList())//어댑터 생성
        viewPager_ljh.orientation = ViewPager2.ORIENTATION_HORIZONTAL //방향을 가로로
        viewPager_ljh.setPageTransformer(ZoomOutPageTransformer()) //애니메이션 적용
    }
    private fun getLjhList(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.ljh1, R.drawable.ljh2, R.drawable.ljh3)
    }

    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer{
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when{
                    position<-1->{
                        alpha = 0f
                    }
                    position <=1->{
                        val scaleFactor = Math.max(MIN_SCALE, 1-Math.abs(position))
                        val vertMargin = pageHeight * (1-scaleFactor)/2
                        val horzMargin = pageWidth * (1-scaleFactor)/2
                        translationX = if (position < 0){
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin/2
                        }
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        alpha = (MIN_ALPHA+(((scaleFactor - MIN_SCALE)/(1-MIN_SCALE))*(1-MIN_ALPHA)))
                    }
                    else ->{
                        alpha = 0f
                    }
                }
            }
        }
    }
}