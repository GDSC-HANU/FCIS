# Giới thiệu
- Mục đích của project này là để demo kiến trúc Imperative Shell, Functional Core

# Imperative Shell, Functional Core Architecture
- Vấn đề giải quyết: giúp code dễ đọc, dễ unit test, giảm độ phức tạp codebase (cylomatic complexity & cogitive complexity), ít bug hơn.

- Nhắc lại chút về **Clean Architecture**, khi dùng kiến trúc này, ta sẽ tách phần mềm thành 2 tầng:
    - **Domain**: implement tất cả logic nghiệp vụ tại tầng này
    - **Infrastructure**: implement tất cả logic tương tác với các technology bên ngoài như database, queue, HTTP,...

- **Imperative Shell, Functional Core Architecture** (gọi tắt là **FCIS**) chính là **Clean Architecture**, chỉ đặc biệt ở chỗ, **FCIS** bắt buộc sử dụng Functional Programming cho tầng Domain.

# Functional Programming
- Vấn đề giải quyết: giúp code dễ đọc, dễ unit test, giảm độ phức tạp codebase (cylomatic complexity & cogitive complexity), ít bug hơn.

- Cách giải quyết:
    - **Pure functions**: Function không có side effect bao gồm: không throw Exception, không mutate biến global. Pure functions sẽ giúp code dễ đọc và dễ unit test. Mỗi loại input truyền vào function đều trả về một loại output cố định, có thể đoán trước được.
    - **Immutablity**:   Tất cả mọi biến đều là final, một khi được tạo ra thì không thể được thay đổi. Immutablity sẽ giúp giảm độ phức tạp codebase, giúp làm việc multi-thread hiệu quả hơn, giúp debug dễ hơn.

# Ứng dụng trong project này

- Áp dụng **Imperative Shell, Functional Core Architecture**:
    - Package chính được chia thành hai Package con: Domain và Infrastructure
    - Package Domain sẽ được áp dụng Functional Programming toàn bộ
    - Package Infrastructure cũng được áp dụng Functional Programming nhiều nhất có thể

- Áp dụng **Functional Programming**:
    - Áp dụng **Pure functions**: Function không được throw Exception, không được mutate biến global (mà cũng sẽ không được phép có biến global nào mutate được đâu)
    - Áp dụng **Immutability**: tất cả mọi biến được khai báo đều phải là final.
        - Có một số trường hợp mà dùng biến mutate sẽ giúp giải quyết vấn đề dễ dàng hơn, vẫn được, tuy nhiên chỉ được dùng nó trong một function CỰC KỲ NHỎ, và không được share biến mutate ra ngoài phạm vi function đó.
        - Thay vì dùng các mutable data structure của Java như List, Set, Map ta sẽ dùng ImmutableList, ImmutableSet, ImmutableMap của thư viện Google Guava

Note: khi xem code, hãy xem file **com.gdsc.inventory.domain.model.Inventory** trước vì trong đó có khá nhiều comment hướng dẫn.
