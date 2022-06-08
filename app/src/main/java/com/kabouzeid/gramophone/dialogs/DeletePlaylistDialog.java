package com.kabouzeid.gramophone.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kabouzeid.gramophone.R;
import com.kabouzeid.gramophone.model.Playlist;
import com.kabouzeid.gramophone.model.Song;
import com.kabouzeid.gramophone.util.PlaylistsUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class DeletePlaylistDialog extends DialogFragment {
    @NonNull
    public static DeletePlaylistDialog create(Playlist playlist) {
        List<Playlist> list = new ArrayList<Playlist>();
        list.add(playlist);
        return create(list);

        //private List<UpdateList> updateLists = new ArrayList<UpdateList>();
    }

    /*public class UpdateListDialog extends DeletePlaylistDialog {
        private List<UpdateList> updateLists;
    }*/
    /*
    public List<Playlist> getSong() {
        return list;
    }

    public void addUpdate(UpdateList updateList) {
        updateLists.add(updateList);
    }
    public void addSong(Playlist playlist)
    {
        list.add(playlist);
        for (UpdateList updateList: updateLists )
            updateList.update();
    }*/

    private Song song;

    class DeletePlayList throws Exception {

        @BeforeEach

        void setUp() {
        }

        @Test
        void testaddSong() {
            song = new Song();
            assertEquals(0, song.add(song));
        }

        @Test
        void testPushList() {
            song = new Song();
            song.push('list');
            assertEquals('list', song.pop());
        }
    }


    @NonNull
    public static DeletePlaylistDialog create(List<Playlist> playlists) {
        DeletePlaylistDialog dialog = new DeletePlaylistDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("playlists", new ArrayList<>(playlists));
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection unchecked
        final List<Playlist> playlists = getArguments().getParcelableArrayList("playlists");
        int title;
        CharSequence content;
        //noinspection ConstantConditions
        title = R.string.delete_playlists_title;
        if (playlists.size() > 1) {
            content = Html.fromHtml(getString(R.string.delete_x_playlists, playlists.size()));
        } else {
            content = Html.fromHtml(getString(R.string.delete_playlist_x, playlists.get(0).name));
        }
        return new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText(R.string.delete_action)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    if (getActivity() == null)
                        return;
                    PlaylistsUtil.deletePlaylists(getActivity(), playlists);
                })
                .build();
    }
}
