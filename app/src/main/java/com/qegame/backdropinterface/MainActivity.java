
package com.qegame.backdropinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qegame.bottomappbarcustom.BottomAppBarCustom;
import com.qegame.materialinterface.MaterialInterface;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity-ИНФ";

    MaterialInterface materialInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialInterface = findViewById(R.id.material_interface);

        materialInterface.getBar().setConstruction(getFabCenter());

        materialInterface.setSubtitle("Subtitle");

        /*materialInterface.removeAllViewInBack();
        for (int i = 0; i < 10; i++) {
            materialInterface.addViewToBack(new Button(this));
        }

        materialInterface.buildFirstIcon(new BottomAppBarCustom.IconSettings() {
            @Override
            public Drawable getImage() {
                return getResources().getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("Icon first clicked!");
            }
        });

        materialInterface.setFrontShape(MaterialInterface.FrontShape.ALL_ROUND);*/
        materialInterface.setContentPadding(50, 50, 50, 0);


        View view = getLayoutInflater().inflate(R.layout.content, materialInterface.getContentContainer(), false);
        materialInterface.setContentView(view);
        materialInterface.performClickBottomIcon(1);

    }

    private BottomAppBarCustom.Construction.FABEnd getFabEnd() {
        BottomAppBarCustom.FABSettings fab = new BottomAppBarCustom.FABSettings() {
            @Override
            public Drawable getImage() {
                return null;
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.getBar().setConstruction(getFabCenter());
                    }
                };
            }
        };

        BottomAppBarCustom.IconSettings icon_0 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_0");
            }
        };
        BottomAppBarCustom.IconSettings icon_1 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_1");
            }
        };
        BottomAppBarCustom.IconSettings icon_2 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_2");
            }
        };

        return new BottomAppBarCustom.Construction.FABEnd(fab, icon_0, icon_1, icon_2);
    }
    private BottomAppBarCustom.Construction.FABCenter getFabCenter() {
        BottomAppBarCustom.FABSettings fab = new BottomAppBarCustom.FABSettings() {
            @Override
            public Drawable getImage() {
                return null;
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialInterface.getBar().setConstruction(getFabEnd());
                    }
                };
            }
        };

        BottomAppBarCustom.IconSettings icon_0 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                Log.e(TAG, "getClickListener: ");
                return new ToastListener("icon_0");
            }
        };
        BottomAppBarCustom.IconSettings icon_1 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_1");
            }
        };
        BottomAppBarCustom.IconSettings icon_2 = new BottomAppBarCustom.IconSettings() {

            @Override
            public Drawable getImage() {
                return getDrawable(R.drawable.navigation_icon_close);
            }

            @Override
            public View.OnClickListener getClickListener() {
                return new ToastListener("icon_2");
            }
        };

        return new BottomAppBarCustom.Construction.FABCenter(fab, new BottomAppBarCustom.IconSettings[]{icon_0, icon_1}, new BottomAppBarCustom.IconSettings[]{icon_2});
    }

    private class ToastListener implements View.OnClickListener {
        String text;
        public ToastListener(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View v) {
            Log.e(TAG, "onClick: ");
            materialInterface.getBar().showSnackBar(text);
        }
    }
    
}
