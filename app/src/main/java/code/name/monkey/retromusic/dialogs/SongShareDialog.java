package code.name.monkey.retromusic.dialogs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import code.name.monkey.retromusic.R;
import code.name.monkey.retromusic.model.Song;
import code.name.monkey.retromusic.util.MusicUtil;
import code.name.monkey.retromusic.views.RoundedBottomSheetDialogFragment;


public class SongShareDialog extends RoundedBottomSheetDialogFragment {
    @BindView(R.id.option_2)
    TextView currentSong;

    @NonNull
    public static SongShareDialog create(final Song song) {
        final SongShareDialog dialog = new SongShareDialog();
        final Bundle args = new Bundle();
        args.putParcelable("song", song);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.dialog_file_share, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Song song = getArguments().getParcelable("song");
        currentSong.setText(getString(R.string.currently_listening_to_x_by_x, song.title, song.artistName));
    }

    @OnClick({R.id.option_2, R.id.option_1})
    void onClick(View view) {
        final Song song = getArguments().getParcelable("song");
        final String currentlyListening = getString(R.string.currently_listening_to_x_by_x, song.title, song.artistName);
        switch (view.getId()) {
            case R.id.option_1:
                startActivity(
                        Intent.createChooser(
                                MusicUtil.createShareSongFileIntent(song, getContext()), null));
                break;
            case R.id.option_2:
                getActivity().startActivity(Intent.createChooser(
                        new Intent().setAction(Intent.ACTION_SEND)
                                .putExtra(Intent.EXTRA_TEXT, currentlyListening)
                                .setType("text/plain"), null));
                break;
        }
        dismiss();
    }
}
