# URLs

### @RequestMapping("/booking")

```java 
@PostMapping("/list")
public List<Booking> queryBookings()
```
>Params: 
- none
>RetVal: 
- If exists: \
  returns {"status","true"} \
  followed by a list of booking information in the format {"Booking","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findCourseByStu")
@ResponseBody
public List<Course> findCourseByStu(@RequestBody Map<String, Object> param)
```
> Params:
- `@String stuId`: the id of target user
> RetVal: 
- If exists: \
  returns {"status","true"} \
  followed by a list of course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/add")
@ResponseBody
public boolean addBooking(@RequestBody Map<String,Object> param)
```
> Params:
- `@String stuId`: the id of target user
- `@int courseId`: the id of the course to be booked
- `@String phone`: the phone number of the user
> RetVal:
- Returns true if the booking info is successfully inserted

---
<br>
<br>
<br>
<br>
<br>

---
### @RequestMapping("/course")
```java
@PostMapping("/list")
public List<Course> queryCourses()
```
> Params:
- none
> RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findCourseById")
@ResponseBody
public Course findCourseById(@RequestBody Map<String,Object> param)
```
>Params:\
- `@int courseId`: the id of the course to be seeked

> RetVal:
- If exists: \
  returns {"status","true"} \
  followed by detailed course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findCourseByDate")
@ResponseBody
public List<Course> findCourseByDate(@RequestBody Map<String,Object> param)
```
>Params:\
- `@String date`: the selected date
> RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findCourseByTitle")
@ResponseBody
public List<Course> findCourseByTitle(@RequestBody Map<String,Object> param)
```
>Params:\
- `@String title`: restricted title for courses
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/add")
@ResponseBody
public synchronized int addCourse(@RequestBody Map<String,Object> param)
```
>Params:
- `@String title`: the title of the course to be added into the database
- `@String des`: detailed description of the course
- `@String stuId`: the student id of the lecturer
- `@String location`: where the course will be given
- `@String date`: when the course will be given
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed {"courseID",int value}
- If not : \
  returns {"status","false"}

---
<br>
<br>
<br>
<br>
<br>

---
### @RequestMapping("/release")
```java
@PostMapping("/list")
public List<Release> queryCourses()
```
>Params:
- none
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of booking information in the format {"Release","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findCourseByTea")
@ResponseBody
public List<Course> findCourseByTea(@RequestBody Map<String, Object> param)
```
>Params:
- `@String stuId`: the id of the student who will give the lecture
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of Course information in the format {"Course","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/add")
@ResponseBody
public boolean addRelease(@RequestBody Map<String, Object> param)
```
>Params:
- `@String stuId`: the id of the student who will be the lecturer
- `@int courseId`: the id of the course which will be taught by the lecturer
- `@String phone`: the phone number of the lecturer
>RetVal:
- Returns true if the info is successfully inserted
- Returns false if not

---

<br>
<br>
<br>
<br>
<br>

---

### @RequestMapping("/reply")
```java
@PostMapping("/add")
@ResponseBody
public synchronized int addReply(@RequestBody Map<String,Object> param)
```
>Params:
- `@int topicId`: the id of the topic which the reply belongs
- `@String des`: detailed description of the reply
- `@String stuId`: the student number of the author
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed {"replyID",int value}
- If not : \
  returns {"status","false"}

---

```java
@PostMapping("/addLikes")
@ResponseBody
public boolean addLikesToReply(@RequestBody Map<String,Object> param)
```
>Params:
- `@int replyId`: the reply to which the like will be sent 
- `@String stuId`: the id of the student who send the like
>RetVal:
- Returns true if like is successfully sent
- Returns false if this user had already sent a like to this reply

---

```java
@PostMapping("/getSumLikes")
@ResponseBody
public int getSumLikes(@RequestBody Map<String,Object> param)
```
>Params:
- `@int replyId:` the targeted reply
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed by {"Sum",Integer}
- If not (eg, replyId not exists): \
  returns {"status","false"}

---

<br>
<br>
<br>
<br>
<br>

---

### @RequestMapping("/topic")

```java
@PostMapping("/list")
public List<Topic> queryTopics()
```
>Params:
- none
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of booking information in the format {"Topic","***"}
- If not exist: \
  returns {"status","false"}


---

```java
@PostMapping("/findTopicById")
@ResponseBody
public Topic findTopicById(@RequestBody Map<String,Object> param)
```
>Params:
- `@int topicId`: the id of the targeted topic
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by detailed topic information in the format {"Topic","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findTopicByAuthor")
@ResponseBody
public List<Topic> findTopicByAuthor(@RequestBody Map<String,Object> param)
```
>Params:
- `@String stuId`: the id of the student who is the author of some topics
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of booking information in the format {"Topic","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/findRepliesByTopic")
@ResponseBody
public List<Reply> findRepliesByTopic(@RequestBody Map<String,Object> param)
```
>Params:
- `@int topicId`: the id a topic
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of reply information in the format {"Reply","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/add")
@ResponseBody
public synchronized int addTopic(@RequestBody Map<String,Object> param)
```
>Params:
- `@String title`: the title of the topic, for example, the OS
- `@String des`: detailed description of the topic
- `@String stuId`: the student id of the author
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed {"topicID",int value}
- If not : \
  returns {"status","false"}

---

```java
@PostMapping("/addLikes")
@ResponseBody
public boolean addLikesToTopic(@RequestBody Map<String,Object> param)
```
>Params:
- `@String stuId`: the id of the student who sent the like
- `@int topicId`: the id of the topic to which the like will be sent
>RetVal:
- Returns true if the like is successfully sent
- Returns false if already liked

---

```java
@PostMapping("/getSumLikes")
@ResponseBody
public int getSumLikes(@RequestBody Map<String,Object> param)
```
>Params:
- `@int topicId`: the targeted topic
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed by {"Sum",Integer}
- If not (eg, topicId not exists): \
  returns {"status","false"}
---

```java
@PostMapping("/getSumReplies")
@ResponseBody
public int getSumReplies(@RequestBody Map<String,Object> param)
```
>Params:
- `@int topicId`: the targeted topic
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed by {"Sum",Integer}
- If not (eg, topicId not exists): \
  returns {"status","false"}

---

```java
@PostMapping("/uploadPic")
@ResponseBody
public boolean uploadPic(@RequestBody Map<String,Object> param) throws Exception
```
>Params:
- @int topicId: the targeted topic
- @String url: where the picture stores
>RetVal:
- Returns true

---

<br>
<br>
<br>
<br>
<br>

---

### @RequestMapping("/user")
```java
@GetMapping("/list")
public List<User> queryUsers()
```
>Params:
- none
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by a list of user information in the format {"User","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/register")
@ResponseBody
public boolean addUser(@RequestBody Map<String,Object> param)
```
>Params:
- `@String stuId`: the student id of the user
- `@String js_code`: js code used to get open id
- `@String name`: the name of the user
>RetVal:
- Returns true if the info is successfully inserted
- Returns false if not

---

```java
@PostMapping("/login")
@ResponseBody
public userInfo login(@RequestBody Map<String,Object> param)
```
>Params:
- `@String js_code`: js code used to get open id
>RetVal:
- If exists: \
  returns {"status","true"} \
  followed by detailed user information in the format {"userInfo","***"}
- If not exist: \
  returns {"status","false"}

---

```java
@PostMapping("/uploadAvatar")
@ResponseBody
public boolean uploadAvatar(@RequestBody Map<String,Object> param) throws Exception
```
>Params:
- `@String url`: where the picture stores
- `@String stuId`: the targeted user
>RetVal:
- Returns true






<br>
<br>
<br>
---
# Supplementation

### CourseController
- Add function: upload picture
```java
@PostMapping("/uploadPic")
@ResponseBody
public boolean uploadPic(@RequestBody Map<String, Object> param)
```

### ATTENTION:
A new folder named "coursePic" should be added at the same directory as topicPic and Avatar.

<br>

---

<br>

- Revised function: add verification for the courses as well as add a new category indentifier
```java
@PostMapping("/add")
@ResponseBody
public synchronized Map<String,Object> addCourse(@RequestBody Map<String, Object> param)
```
>Params:
- `@String title`: the title of the course to be added into the database
- `@String des`: detailed description of the course
- `@String stuId`: the student id of the lecturer
- `@String location`: where the course will be given
- `@String date`: when the course will be given
- `@String category`: the category it belongs to
>RetVal:
- If successful: \
  returns {"status","true"} \
  followed {"courseID",int value}
- If not : \
  returns {"status","false"}

### ATTENTION:
the attributes are revised to :
- private int id;
- private String title;
- private String des;
- private String lecturer;
- private String date;
- private String location;
- private int verification;
- private String category;\
`Here` for verification, 0 stands for unverified while 1 stands for verified. And the database should be changed accordingly, that is to say, add the attribute verification and the attribute category to the table course.

<br>

---

<br>

- Added function: verify a course

```java
@PostMapping("/verify")
@ResponseBody
public boolean verifyCourse(@RequestBody Map<String, Object> param)
```
>Params:
- `@String courseId`: the ID of the course verified
>RetVal:
- If successful: \
  returns true
- If not : \
  returns false

<br>

---

<br>

- Added function: change the location of the course
```java
@PostMapping("/changeLocation")
@ResponseBody
public boolean changeLoc(@RequestBody Map<String,Object> param)
```
>Params:
- `@String courseId`: the ID of the course verified
- `@String location`: the new location
>RetVal:
- If successful: \
  returns true
- If not : \
  returns false

<br>

---

<br>

- Added function: change date of the course
```java
@PostMapping("/changeDate")
@ResponseBody
public boolean changeDate(@RequestBody Map<String,Object> param)
```
>Params:
- `@String courseId`: the ID of the course verified
- `@String date`: the new date
>RetVal:
- If successful: \
  returns true
- If not : \
  returns false

  <br>

  ---
  
  <br>

  ### Release Controller
  ---

- revised function: the true name of the teacher are needed.
```java
@PostMapping("/add")
@ResponseBody
public boolean addRelease(@RequestBody Map<String, Object> param)
```
>Params:
- `@String stuId`: the id of the student who will be the lecturer
- `@int courseId`: the id of the course which will be taught by the lecturer
- `@String phone`: the phone number of the lecturer
- `@String trueName`: the true name of the lecturer
>RetVal:
- Returns true if the info is successfully inserted
- Returns false if not

### ATTENTION:
The table release should add one more attribute named "trueName".
