import java.util.*;
//Building PlayList myself with a doubly linked list, Library and playcounts will be reused using HashMap in java. Up next and History are being resued using ArrayDeque for both/
public class MusicPlayer{
    private Node head;
    private Node tail;
    private Map<String, Node> library = new HashMap<>(); //library
    private Deque<Song> upNext = new ArrayDeque<>();//upNext
    private Deque<Song> history = new ArrayDeque<>(); //history
    private Map<String, Integer> playCounts = new HashMap<>(); //playCounts
    private Song current;
    //Playlist

    public static class Node {
        Song song;
        Node prev;
        Node next;

        public Node(Song song) {
            this.song = song;
            this.prev = null;
            this.next = null;
        }
    }

    public boolean addSong(String id, String title, String artist) {
        if (library.containsKey(id)) return false;
        Node newSong = new Node(new Song(id, title, artist));
        if(head == null) {
            head = newSong;
            tail = newSong;
        }
        else{
            tail.next = newSong;
            newSong.prev = tail;
            tail = newSong;
        }
        library.put(id, newSong);
        return true;

    }

    public Song findSong(String id){
        Node n = library.get(id);
        if(n == null) return null;
        else{ return n.song;}
    }

    public List<String> showPlaylist(){
        List<String> playlist = new ArrayList<String>();
        Node n = head;
        while(n != null){
            playlist.add(n.song.title);
             n = n.next;
        }
        return playlist;
        }

    // private helper for start song

    private void begin(Song s){
        current = s;
        playCounts.merge(s.id, 1, Integer :: sum);
    }
    public Song play(String id){
        Node l = library.get(id);
        if( l == null) return null;
        if(current != null) history.push(current);
        begin(l.song);
        return current;
    }

    public Song nowPlaying(){
        return current;
    }

    public int playCount(String id){
        return playCounts.getOrDefault(id, 0);
    }

    public boolean queueLater(String id){
        Node n = library.get(id);
        if( n == null) return false;
        else{ upNext.addLast(library.get(id).song);}
        return true;
        }

    public boolean queueNext(String id){
        Node n = library.get(id);
        if( n == null) return false;
        else{ upNext.addFirst(library.get(id).song);}
        return true;
    }
    public List<String> showUpNext(){
        List<String> afterNext = new ArrayList<String>();
        for(Song s : upNext){
            if(library.containsKey(s.id)) afterNext.add(s.title);

        }
        return afterNext;
    }

    public Song next(){
        Song target = null;

        while(!upNext.isEmpty()){
            Song s = upNext.pollFirst();
            if (library.containsKey(s.id)){
                target = s;
                break;
            }
        }

        if(target == null && current!= null){
            Node c = library.get(current.id);
            if(c.next != null){
                target = c.next.song;
            }
        }

        if(target == null) return null;

        if(current != null) history.push(current);
        begin(target);
        return target;
    }

    public Song back(){
        Song target = null;

        while(!history.isEmpty()){
            Song s = history.pollFirst();
            if (library.containsKey(s.id)){
                target = s;
                break;
            }
        }

        if(target == null) return null;

        if(current != null) upNext.addFirst(current);
        begin(target);
        return target;
    }

    public List<String> showHistory(){
        List<String> shistory = new ArrayList<String>();
        for(Song s : history){
            if(library.containsKey(s.id)) shistory.add(s.title);

        }
        return shistory;
    }

    public boolean removeSong(String id){
       Node e =  library.get(id);
       if(e == null) return false;
       if(current != null && id.equals(current.id)) return false;

       if (e.prev == null && e.next == null) {
            head = null;
            tail = null;
       } else if (e.prev == null) {
           head = e.next;
           head.prev = null;
       } else if (e.next == null) {
           tail = e.prev;
           tail.next =  null;
       } else {
           e.prev.next = e.next;
           e.next.prev = e.prev;
       }

       library.remove(id);
       playCounts.remove(id);
       return true;
    }

    public List<String> topSongs(int n){
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(playCounts.entrySet());
        entries.sort((a,b) -> Integer.compare(b.getValue(), a.getValue()));

        List<String> result = new ArrayList<>();
        for(int i = 0; i < entries.size() && i < n; i++){
            String id = entries.get(i).getKey();
            result.add(library.get(id).song.title);
        }
        return result;
    }

    public void shuffleUpNext(){
        List<Song> songs = new ArrayList<>();
        Node n = head;
        while(n !=null){
            songs.add(n.song);
            n = n.next;
        }

        Random shuff = new Random();
        for(int i = songs.size() - 1; i>0 ; i --){
            int j = shuff.nextInt(i + 1);
            Song temp = songs.get(i);
            songs.set(i, songs.get(j));
            songs.set(j,  temp);
        }
        upNext.clear();
        for(Song s : songs){
            upNext.addLast(s);
        }
    }

}



