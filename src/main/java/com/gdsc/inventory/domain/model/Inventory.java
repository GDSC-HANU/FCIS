package com.gdsc.inventory.domain.model;

import com.gdsc.inventory.domain.exception.InsufficientCapacityException;
import com.gdsc.shared.domain.exception.ValidateException;
import com.gdsc.shared.domain.model.Id;
import com.gdsc.shared.domain.model.ImmutableListHelper;
import com.gdsc.shared.domain.model.PositiveDouble;
import com.gdsc.shared.domain.model.Result;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

/*
Hai annotation này bắt buộc phải có PRIVATE, nhằm ép việc
tạo Inventory bắt buộc phải đi qua hàm create()
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
public class Inventory {
    /*
    Tất cả các field đều phải là final
    để ngăn việc data có thể bị thay đổi
     */
    public final Id id;
    public final PositiveDouble height;
    public final PositiveDouble length;
    public final PositiveDouble width;
    public final ImmutableList<InventoryItem> inventoryItems;

    /*
    Lưu ý khi validate fail, ta sẽ không throw exception,
    vì function trong Functional Programming không có side effect,
    mà việc throw exception khiến function có side effect.
    Vì vậy, ta sẽ trả lại object Result<?,?> với 1 trong 2 trạng thái
    success hoặc failed (thay cho việc throw exception)

    Để tạo ra object Inventory, client sẽ bắt buộc phải gọi qua hàm này
    do constructor đã được làm private (sử dụng lombok annotation @AllArgsConstructor...)
    Data sẽ chắc chắn được validate trước khi được tạo
    */
    public static Result<Inventory, ValidateException> create(Id id,
                                                              PositiveDouble height,
                                                              PositiveDouble length,
                                                              PositiveDouble width,
                                                              ImmutableList<InventoryItem> inventoryItems) {
        if (id == null)
            return Result.failed(new ValidateException("id must not be null"));
        if (height == null)
            return Result.failed(new ValidateException("height must not be null"));
        if (length == null)
            return Result.failed(new ValidateException("length must not be null"));
        if (width == null)
            return Result.failed(new ValidateException("width must not be null"));
        if (inventoryItems == null)
            return Result.failed(new ValidateException("inventoryItems must not be null"));
        final Inventory inventory = Inventory.builder()
                .id(id)
                .height(height)
                .length(length)
                .width(width)
                .inventoryItems(inventoryItems)
                .build();
        return Result.success(inventory);
    }

    /*
    Trong trường hợp cần thay đổi data như trường hợp này (Inventory cần
    được check-in Inventory Item mới) ta sẽ không thay đổi data của
    object hiện tại, mà ta sẽ tạo ra object mới với data mới. Trong trường hợp
    này,ta tạo ra Inventory mới với danh sách Inventory Item mới
     */
    public Result<Inventory, InsufficientCapacityException> checkIn(InventoryItem inventoryItem) {
        if (inventoryItem.volume() > this.remainingCapacity())
            return Result.failed(new InsufficientCapacityException());
        final ImmutableList<InventoryItem> checkedInInventoryItems = ImmutableListHelper.add(inventoryItem, inventoryItems);
        final Inventory checkedInventory = this.toBuilder()
                .inventoryItems(checkedInInventoryItems)
                .build();
        return Result.success(checkedInventory);
    }

    public double remainingCapacity() {
        /*
        Lưu ý: "totalInventoryItemVolume" là biến mutable, nghĩa là nó ko phải final, có thể
        thay đổi trạng thái. Sử dụng biến mutable và dùng vòng for để mutate trong PHẠM VI RẤT NHỎ
        thì vẫn được phép. Tuy nhiên cần lưu ý những điều sau:
        - Chỉ sử dụng biến mutable nếu thật sự cần thiết, không có cách nào khác dễ dàng hơn
        - Chỉ sử dụng biến mutable trong function cực kỳ nhỏ như function này
        - Không được share biến mutable ra khỏi phạm vi function nó được dùng
         */
        double totalInventoryItemVolume = 0;
        for (InventoryItem inventoryItem : inventoryItems)
            totalInventoryItemVolume += inventoryItem.volume();
        return totalCapacity() - totalInventoryItemVolume;
    }

    public Result<Inventory, InsufficientCapacityException> checkIn(ImmutableList<InventoryItem> inventoryItems) {
        /*
        Giống method trên, "inventoryToCheckIn" là mutable, tuy nhiên
        nó chỉ tồn tại trong scope function cực kì nhỏ này, nên không vấn đề gì cả
         */
        Inventory inventoryToCheckIn = this;
        for (InventoryItem inventoryItem : inventoryItems) {
            final Result<Inventory, InsufficientCapacityException> checkInOneInventoryItemResult = inventoryToCheckIn.checkIn(inventoryItem);
            if (checkInOneInventoryItemResult.isFailed())
                return checkInOneInventoryItemResult;
            inventoryToCheckIn = checkInOneInventoryItemResult.successData;
        }
        return Result.success(inventoryToCheckIn);
    }


    public double totalCapacity() {
        return height.toDouble() * width.toDouble() * length.toDouble();
    }
}
