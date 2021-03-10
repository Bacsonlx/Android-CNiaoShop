package hznu.linxin.fresco;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends AppCompatActivity {

    // 使用XUtils代替findViewbyId
    @ViewInject(R.id.image_view)
    private SimpleDraweeView draweeView;

    private  String img_url="http://gb.cri.cn/mmsource/images/2014/08/22/38/10202051462353342906.jpg";
    private  String img_url1="http://heilongjiang.sinaimg.cn/2015/0326/U10061P1274DT20150326104659.jpg";
    private  String img_url2="http://img4q.duitang.com/uploads/item/201411/20/20141120132318_3eAuc.thumb.700_0.jpeg";
    private  String img_url3="http://hiphotos.baidu.com/%CC%EC%C9%BD%B6%FE%CF%C0%B5%C4%D0%A1%CE%DD/pic/item/70c553e736d12f2e5b0614d64fc2d5628535682a.jpg";
    private  String img_url4="http://img4.douban.com/view/photo/raw/public/p1773847919.jpg";
    private  String img_url5="http://7mno4h.com2.z0.glb.qiniucdn.com/3859deb07d4647cf9183f8ea3f5aa165.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 使用XUtils代替findViewbyId()
        ViewUtils.inject(this);
        // 加载一张图片
        Uri uri = Uri.parse(img_url);
        draweeView.setImageURI(uri);
//        // 渐进式加载图片
//        requestImage();
//        // 加载多张图片
//        moreImages();
    }

    /**
     *  渐进式显示网络图片
     */
    private void requestImage(){

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(img_url2))
                .setProgressiveRenderingEnabled(true)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request).build();
        draweeView.setController(controller);

    }

    private void moreImages(){


        ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(
                    String id,
                    ImageInfo imageInfo,
                    Animatable anim) {

                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id,  ImageInfo imageInfo) {
                FLog.d("","Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }


        };


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(img_url5))
                .setImageRequest(ImageRequest.fromUri(img_url5+"/test"))
                .setControllerListener(listener)
                .build();

        draweeView.setController(controller);

    }
}