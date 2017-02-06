package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.here.android.mpa.common.RoadElement;
import com.here.android.mpa.routing.RouteElement;
import com.here.android.mpa.routing.TransitRouteElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwas7493 on 9/9/2016.
 *
 * Class that serves as the adapter for contents display in recycler
 * for arcgis online group display.
 */
public class DialogViewAdapter extends RecyclerView.Adapter<DialogViewAdapter.ViewHolder> {

    private View dialogView;
    private List<RouteElement> routeElementList;
    public List<String> portalGroupId = new ArrayList<>();



    public DialogViewAdapter(List<RouteElement> routeArrayList){
        this.routeElementList = routeArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        dialogView = LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(dialogView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
                TextView routeInfo = (TextView) holder.textView;
        ImageView routeImage = holder.imageView;
        TransitRouteElement element = routeElementList.get(position).getTransitElement();
        RoadElement roadElement = routeElementList.get(position).getRoadElement();
                if(element != null){
                    routeInfo.setText("Line: "+element.getLineName()+" leaving at: "+element.getDepartureTime().getTime());
                    if(element.getSystemLogo() != null){
                        routeImage.setImageBitmap(element.getSystemAccessLogo().getBitmap());
                    }

                }else if(roadElement != null){
                    routeInfo.setText("Road Name: "+roadElement.getRoadName());
                }
    }

    @Override
    public int getItemCount() {
        return routeElementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View textView;
        public ImageView imageView;
        public ViewHolder(View view){
            super(view);
            this.textView = view.findViewById(R.id.route_directions);
            this.imageView = (ImageView) view.findViewById(R.id.route_images_view);
        }
    }
}
