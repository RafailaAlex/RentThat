package com.example.rentthat.utils



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rentthat.R
import com.example.rentthat.model.Propertie


class PropertiesRecyclerViewAdapterForProfile( var properties: MutableLiveData<ArrayList<Propertie>>, var itemClickListener:onItemClickListener) :
    RecyclerView.Adapter<PropertiesRecyclerViewAdapterForProfile.ViewHolder>() {
    fun submitList( n: ArrayList<Propertie>){
        //properties.value=n
        //   properties.value=n
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =LayoutInflater.from(parent.context).inflate(R.layout.itemprofile,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(properties.value != null)
            return properties.value!!.size

        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(properties.value!=null){
            var p:Propertie = properties.value!!.get(position)
//            holder.desctxt.text=p.description
//            holder.adrtxt.text=p.adress
//            holder.typetxt.text=p.type.toString()
//            holder.pricetxt.text=p.price.toString()

            holder.bind(p,itemClickListener)

        }else{
            holder.idtxt.text=""
            holder.desctxt.text=""
            holder.adrtxt.text=""
            holder.typetxt.text=""
            holder.pricetxt.text=""
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idtxt:TextView=itemView.findViewById(R.id.propertiesItemIDTextProfile)
        var desctxt: TextView=itemView.findViewById(R.id.propertiesItemDescriptionTextProfile)
        var adrtxt: TextView=itemView.findViewById(R.id.propertieItemAdressTextProfile)
        var typetxt:TextView=itemView.findViewById(R.id.propertieItemTypeTextProfile)
        var pricetxt:TextView=itemView.findViewById(R.id.propertiesItemPriceTextProfile)

        fun bind(propertie: Propertie, clickListener: onItemClickListener){
            idtxt.text=propertie.id.toString()
            desctxt.text=propertie.description
            adrtxt.text=propertie.adress
            typetxt.text=propertie.type.toString()
            pricetxt.text=propertie.price.toString()+" RON"
            itemView.setOnClickListener {
                clickListener.onItemClick(propertie,adapterPosition)

            }
        }
    }
    interface onItemClickListener{
        fun onItemClick(propertie:Propertie,position: Int)
    }
}