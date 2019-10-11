```java
class Booking {
    private String stuId;
    private int courseId;
    private String phone;
}
```

```java
class Course {
    private int id;
    private String title;
    private String des;
    private String lecturer;
    private String date;
    private String location;
}
```

```java
class Release {
    private String teaId;
    private int courseId;
    private String phone;
}
```

```java
class Reply {
    private int id;
    private int topic_id;
    private String des;
    private String author;
    private int likes;
}
```

```java
class Topic {
    private int id;
    private String title;
    private String des;
    private String author;
    private int likes;
    private int replies;
}
```

```java
class User {
    private String ID;
    private String name;
    private String open_id;
    private int identity;
}
```

```java
class userInfo {
    private int identity;
    private String name;
    private String id;
}
```