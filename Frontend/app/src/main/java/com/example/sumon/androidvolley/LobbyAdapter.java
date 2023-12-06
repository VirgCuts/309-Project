package com.example.sumon.androidvolley;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Lobby lobby);
    }
    private List<Lobby> lobbyList;
    private OnItemClickListener listener;



    public LobbyAdapter(List<Lobby> lobbyList, OnItemClickListener listener) {
        this.lobbyList = lobbyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lobby lobby = lobbyList.get(position);
        holder.bind(lobby);
    }

    @Override
    public int getItemCount() {
        return lobbyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLobbyName, tvNumberOfUsers, tvGameState;
        OnItemClickListener listener; // Hold a reference to the listener

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener; // Assign the listener
            tvLobbyName = itemView.findViewById(R.id.tvLobbyName);
            tvNumberOfUsers = itemView.findViewById(R.id.tvNumberOfUsers);
            tvGameState = itemView.findViewById(R.id.tvGameState);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick((Lobby) itemView.getTag()); // Use the tag
                    }
                }
            });
        }

        public void bind(Lobby lobby) {
            itemView.setTag(lobby); // Set the tag to the lobby object
            String lobbyName = "Lobby "+lobby.getNum();
            tvLobbyName.setText(lobbyName);
            tvGameState.setVisibility(lobby.isGameInProgress() ? View.VISIBLE : View.GONE);
            tvGameState.setText(lobby.isGameInProgress() ? "Game in Progress" : "");
        }
    }
}
