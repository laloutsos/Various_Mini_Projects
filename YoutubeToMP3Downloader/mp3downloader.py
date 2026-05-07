import yt_dlp
import tkinter as tk
from tkinter import messagebox, filedialog  


def download_best_audio(playlist_url, output_folder):

    ydl_opts = {
        'format': 'bestaudio',
        'outtmpl': f'{output_folder}/%(playlist_index)s - %(title)s.%(ext)s',
        'postprocessors': [{
            'key': 'FFmpegExtractAudio',
            'preferredcodec': 'mp3',
            'preferredquality': '192',
        }],
        'postprocessor_args': ['-vn'],
        'quiet': False,
        'noplaylist': False,
        'ignoreerrors': True,
        'nooverwrites': True,
    }

    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.download([playlist_url])


if __name__ == "__main__":
    def choose_folder():
        folder_selected = filedialog.askdirectory()
        if folder_selected:
            folder_label.config(text=folder_selected)
        else:
            folder_label.config(text="No folder selected")

    def start_download():
        url = url_entry.get()
        folder = folder_label.cget("text")
        if not url:
            messagebox.showwarning("⚠️ Missing URL", "Please enter a YouTube playlist URL.")
            return
        if folder == "No folder selected":
            messagebox.showwarning("⚠️ Missing Folder", "Please select a folder to save the files.")
            return
        try:
            download_best_audio(url, folder)
            messagebox.showinfo("✅ Success", "Download completed successfully!")
        except Exception as e:
            messagebox.showerror("❌ Error", str(e))

    root = tk.Tk()
    root.title("🎵 YouTube Playlist MP3 Downloader")
    root.geometry("600x200")

    tk.Label(root, text="Enter YouTube Playlist URL:", font=("Arial", 12)).pack(pady=10)

    url_entry = tk.Entry(root, width=70)
    url_entry.pack()

    tk.Button(root, text="Choose Download Folder", command=choose_folder, bg="#2196F3", fg="white").pack(pady=10)

    folder_label = tk.Label(root, text="No folder selected", fg="red")
    folder_label.pack()

    tk.Button(root, text="Download MP3s", command=start_download, bg="#4CAF50", fg="white").pack(pady=15)

    root.mainloop()
