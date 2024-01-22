{

    function allocate_unbounded() -> memPtr {
        memPtr := mload(64)
    }

    function revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() {
        revert(0, 0)
    }

    function revert_error_c1322bf8034eace5e0b5c7295db60986aa89aae5e0ea0873e4689e076861a5db() {
        revert(0, 0)
    }

    function cleanup_t_uint256(value) -> cleaned {
        cleaned := value
    }

    function validator_revert_t_uint256(value) {
        if iszero(eq(value, cleanup_t_uint256(value))) { revert(0, 0) }
    }

    function abi_decode_t_uint256(offset, end) -> value {
        value := calldataload(offset)
        validator_revert_t_uint256(value)
    }

    function abi_decode_tuple_t_uint256(headStart, dataEnd) -> value0 {
        if slt(sub(dataEnd, headStart), 32) { revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() }

        {

            let offset := 0

            value0 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

    }

    function array_length_t_bytes_memory_ptr(value) -> length {

        length := mload(value)

    }

    function array_storeLengthForEncoding_t_bytes_memory_ptr_fromStack(pos, length) -> updated_pos {
        mstore(pos, length)
        updated_pos := add(pos, 0x20)
    }

    function copy_memory_to_memory_with_cleanup(src, dst, length) {
        let i := 0
        for { } lt(i, length) { i := add(i, 32) }
        {
            mstore(add(dst, i), mload(add(src, i)))
        }
        mstore(add(dst, length), 0)
    }

    function round_up_to_mul_of_32(value) -> result {
        result := and(add(value, 31), not(31))
    }

    function abi_encode_t_bytes_memory_ptr_to_t_bytes_memory_ptr_fromStack(value, pos) -> end {
        let length := array_length_t_bytes_memory_ptr(value)
        pos := array_storeLengthForEncoding_t_bytes_memory_ptr_fromStack(pos, length)
        copy_memory_to_memory_with_cleanup(add(value, 0x20), pos, length)
        end := add(pos, round_up_to_mul_of_32(length))
    }

    function abi_encode_tuple_t_bytes_memory_ptr__to_t_bytes_memory_ptr__fromStack_reversed(headStart , value0) -> tail {
        tail := add(headStart, 32)

        mstore(add(headStart, 0), sub(tail, headStart))
        tail := abi_encode_t_bytes_memory_ptr_to_t_bytes_memory_ptr_fromStack(value0,  tail)

    }

    function cleanup_t_bytes32(value) -> cleaned {
        cleaned := value
    }

    function validator_revert_t_bytes32(value) {
        if iszero(eq(value, cleanup_t_bytes32(value))) { revert(0, 0) }
    }

    function abi_decode_t_bytes32(offset, end) -> value {
        value := calldataload(offset)
        validator_revert_t_bytes32(value)
    }

    function abi_decode_tuple_t_bytes32(headStart, dataEnd) -> value0 {
        if slt(sub(dataEnd, headStart), 32) { revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() }

        {

            let offset := 0

            value0 := abi_decode_t_bytes32(add(headStart, offset), dataEnd)
        }

    }

    function abi_encode_t_uint256_to_t_uint256_fromStack(value, pos) {
        mstore(pos, cleanup_t_uint256(value))
    }

    function abi_encode_tuple_t_uint256__to_t_uint256__fromStack_reversed(headStart , value0) -> tail {
        tail := add(headStart, 32)

        abi_encode_t_uint256_to_t_uint256_fromStack(value0,  add(headStart, 0))

    }

    function abi_decode_tuple_t_uint256t_uint256t_uint256t_uint256t_uint256t_uint256t_uint256(headStart, dataEnd) -> value0, value1, value2, value3, value4, value5, value6 {
        if slt(sub(dataEnd, headStart), 224) { revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() }

        {

            let offset := 0

            value0 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 32

            value1 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 64

            value2 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 96

            value3 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 128

            value4 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 160

            value5 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

        {

            let offset := 192

            value6 := abi_decode_t_uint256(add(headStart, offset), dataEnd)
        }

    }

    function array_length_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(value) -> length {

        length := mload(value)

    }

    function array_storeLengthForEncoding_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_fromStack(pos, length) -> updated_pos {
        mstore(pos, length)
        updated_pos := add(pos, 0x20)
    }

    function array_dataslot_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(ptr) -> data {
        data := ptr

        data := add(ptr, 0x20)

    }

    function abi_encode_t_uint256_to_t_uint256(value, pos) {
        mstore(pos, cleanup_t_uint256(value))
    }

    // struct Utils.record -> struct Utils.record
    function abi_encode_t_struct$_record_$449_memory_ptr_to_t_struct$_record_$449_memory_ptr(value, pos)  {
        let tail := add(pos, 0x60)

        {
            // c0

            let memberValue0 := mload(add(value, 0x00))
            abi_encode_t_uint256_to_t_uint256(memberValue0, add(pos, 0x00))
        }

        {
            // x

            let memberValue0 := mload(add(value, 0x20))
            abi_encode_t_uint256_to_t_uint256(memberValue0, add(pos, 0x20))
        }

        {
            // y

            let memberValue0 := mload(add(value, 0x40))
            abi_encode_t_uint256_to_t_uint256(memberValue0, add(pos, 0x40))
        }

    }

    function abi_encodeUpdatedPos_t_struct$_record_$449_memory_ptr_to_t_struct$_record_$449_memory_ptr(value0, pos) -> updatedPos {
        abi_encode_t_struct$_record_$449_memory_ptr_to_t_struct$_record_$449_memory_ptr(value0, pos)
        updatedPos := add(pos, 0x60)
    }

    function array_nextElement_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(ptr) -> next {
        next := add(ptr, 0x20)
    }

    // struct Utils.record[] -> struct Utils.record[]
    function abi_encode_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_to_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_fromStack(value, pos)  -> end  {
        let length := array_length_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(value)
        pos := array_storeLengthForEncoding_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_fromStack(pos, length)
        let baseRef := array_dataslot_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(value)
        let srcPtr := baseRef
        for { let i := 0 } lt(i, length) { i := add(i, 1) }
        {
            let elementValue0 := mload(srcPtr)
            pos := abi_encodeUpdatedPos_t_struct$_record_$449_memory_ptr_to_t_struct$_record_$449_memory_ptr(elementValue0, pos)
            srcPtr := array_nextElement_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr(srcPtr)
        }
        end := pos
    }

    function abi_encode_tuple_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr__to_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr__fromStack_reversed(headStart , value0) -> tail {
        tail := add(headStart, 32)

        mstore(add(headStart, 0), sub(tail, headStart))
        tail := abi_encode_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_to_t_array$_t_struct$_record_$449_memory_ptr_$dyn_memory_ptr_fromStack(value0,  tail)

    }

    function panic_error_0x41() {
        mstore(0, 35408467139433450592217433187231851964531694900788300625387963629091585785856)
        mstore(4, 0x41)
        revert(0, 0x24)
    }

    function panic_error_0x11() {
        mstore(0, 35408467139433450592217433187231851964531694900788300625387963629091585785856)
        mstore(4, 0x11)
        revert(0, 0x24)
    }

    function checked_add_t_uint256(x, y) -> sum {
        x := cleanup_t_uint256(x)
        y := cleanup_t_uint256(y)
        sum := add(x, y)

        if gt(x, sum) { panic_error_0x11() }

    }

    function checked_sub_t_uint256(x, y) -> diff {
        x := cleanup_t_uint256(x)
        y := cleanup_t_uint256(y)
        diff := sub(x, y)

        if gt(diff, x) { panic_error_0x11() }

    }

    function checked_mul_t_uint256(x, y) -> product {
        x := cleanup_t_uint256(x)
        y := cleanup_t_uint256(y)
        let product_raw := mul(x, y)
        product := cleanup_t_uint256(product_raw)

        // overflow, if x != 0 and y != product/x
        if iszero(
            or(
                iszero(x),
                eq(y, div(product, x))
            )
        ) { panic_error_0x11() }

    }

    function shift_right_1_unsigned(value) -> newValue {
        newValue :=

        shr(1, value)

    }

    function checked_exp_helper(_power, _base, exponent, max) -> power, base {
        power := _power
        base  := _base
        for { } gt(exponent, 1) {}
        {
            // overflow check for base * base
            if gt(base, div(max, base)) { panic_error_0x11() }
            if and(exponent, 1)
            {
                // No checks for power := mul(power, base) needed, because the check
                // for base * base above is sufficient, since:
                // |power| <= base (proof by induction) and thus:
                // |power * base| <= base * base <= max <= |min| (for signed)
                // (this is equally true for signed and unsigned exp)
                power := mul(power, base)
            }
            base := mul(base, base)
            exponent := shift_right_1_unsigned(exponent)
        }
    }

    function checked_exp_unsigned(base, exponent, max) -> power {
        // This function currently cannot be inlined because of the
        // "leave" statements. We have to improve the optimizer.

        // Note that 0**0 == 1
        if iszero(exponent) { power := 1 leave }
        if iszero(base) { power := 0 leave }

        // Specializations for small bases
        switch base
        // 0 is handled above
        case 1 { power := 1 leave }
        case 2
        {
            if gt(exponent, 255) { panic_error_0x11() }
            power := exp(2, exponent)
            if gt(power, max) { panic_error_0x11() }
            leave
        }
        if or(
            and(lt(base, 11), lt(exponent, 78)),
            and(lt(base, 307), lt(exponent, 32))
        )
        {
            power := exp(base, exponent)
            if gt(power, max) { panic_error_0x11() }
            leave
        }

        power, base := checked_exp_helper(1, base, exponent, max)

        if gt(power, div(max, base)) { panic_error_0x11() }
        power := mul(power, base)
    }

    function checked_exp_t_uint256_t_uint256(base, exponent) -> power {
        base := cleanup_t_uint256(base)
        exponent := cleanup_t_uint256(exponent)

        power := checked_exp_unsigned(base, exponent, 0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff)

    }

    function panic_error_0x32() {
        mstore(0, 35408467139433450592217433187231851964531694900788300625387963629091585785856)
        mstore(4, 0x32)
        revert(0, 0x24)
    }

    function revert_error_3538a459e4a0eb828f1aed5ebe5dc96fe59620a31d9b33e41259bb820cae769f() {
        revert(0, 0)
    }

    function finalize_allocation(memPtr, size) {
        let newFreePtr := add(memPtr, round_up_to_mul_of_32(size))
        // protect against overflow
        if or(gt(newFreePtr, 0xffffffffffffffff), lt(newFreePtr, memPtr)) { panic_error_0x41() }
        mstore(64, newFreePtr)
    }

    function allocate_memory(size) -> memPtr {
        memPtr := allocate_unbounded()
        finalize_allocation(memPtr, size)
    }

    function revert_error_5e8f644817bc4960744f35c15999b6eff64ae702f94b1c46297cfd4e1aec2421() {
        revert(0, 0)
    }

    function revert_error_1b9f4a0a5773e33b91aa01db23bf8c55fce1411167c872835e7fa00a4f17d46d() {
        revert(0, 0)
    }

    function revert_error_987264b3b1d58a9c7f8255e93e81c77d86d6299019c33110a076957a3e06e2ae() {
        revert(0, 0)
    }

    function array_allocation_size_t_string_memory_ptr(length) -> size {
        // Make sure we can allocate memory without overflow
        if gt(length, 0xffffffffffffffff) { panic_error_0x41() }

        size := round_up_to_mul_of_32(length)

        // add length slot
        size := add(size, 0x20)

    }

    function abi_decode_available_length_t_string_memory_ptr_fromMemory(src, length, end) -> array {
        array := allocate_memory(array_allocation_size_t_string_memory_ptr(length))
        mstore(array, length)
        let dst := add(array, 0x20)
        if gt(add(src, length), end) { revert_error_987264b3b1d58a9c7f8255e93e81c77d86d6299019c33110a076957a3e06e2ae() }
        copy_memory_to_memory_with_cleanup(src, dst, length)
    }

    // string
    function abi_decode_t_string_memory_ptr_fromMemory(offset, end) -> array {
        if iszero(slt(add(offset, 0x1f), end)) { revert_error_1b9f4a0a5773e33b91aa01db23bf8c55fce1411167c872835e7fa00a4f17d46d() }
        let length := mload(offset)
        array := abi_decode_available_length_t_string_memory_ptr_fromMemory(add(offset, 0x20), length, end)
    }

    function array_allocation_size_t_array$_t_string_memory_ptr_$dyn_memory_ptr(length) -> size {
        // Make sure we can allocate memory without overflow
        if gt(length, 0xffffffffffffffff) { panic_error_0x41() }

        size := mul(length, 0x20)

        // add length slot
        size := add(size, 0x20)

    }

    function revert_error_81385d8c0b31fffe14be1da910c8bd3a80be4cfa248e04f42ec0faea3132a8ef() {
        revert(0, 0)
    }

    // string[]
    function abi_decode_available_length_t_array$_t_string_memory_ptr_$dyn_memory_ptr_fromMemory(offset, length, end) -> array {
        array := allocate_memory(array_allocation_size_t_array$_t_string_memory_ptr_$dyn_memory_ptr(length))
        let dst := array

        mstore(array, length)
        dst := add(array, 0x20)

        let srcEnd := add(offset, mul(length, 0x20))
        if gt(srcEnd, end) {
            revert_error_81385d8c0b31fffe14be1da910c8bd3a80be4cfa248e04f42ec0faea3132a8ef()
        }
        for { let src := offset } lt(src, srcEnd) { src := add(src, 0x20) }
        {

            let innerOffset := mload(src)
            if gt(innerOffset, 0xffffffffffffffff) { revert_error_1b9f4a0a5773e33b91aa01db23bf8c55fce1411167c872835e7fa00a4f17d46d() }
            let elementPos := add(offset, innerOffset)

            mstore(dst, abi_decode_t_string_memory_ptr_fromMemory(elementPos, end))
            dst := add(dst, 0x20)
        }
    }

    // string[]
    function abi_decode_t_array$_t_string_memory_ptr_$dyn_memory_ptr_fromMemory(offset, end) -> array {
        if iszero(slt(add(offset, 0x1f), end)) { revert_error_1b9f4a0a5773e33b91aa01db23bf8c55fce1411167c872835e7fa00a4f17d46d() }
        let length := mload(offset)
        array := abi_decode_available_length_t_array$_t_string_memory_ptr_$dyn_memory_ptr_fromMemory(add(offset, 0x20), length, end)
    }

    function abi_decode_t_uint256_fromMemory(offset, end) -> value {
        value := mload(offset)
        validator_revert_t_uint256(value)
    }

    // struct Utils.voting
    function abi_decode_t_struct$_voting_$442_memory_ptr_fromMemory(headStart, end) -> value {
        if slt(sub(end, headStart), 0x60) { revert_error_3538a459e4a0eb828f1aed5ebe5dc96fe59620a31d9b33e41259bb820cae769f() }
        value := allocate_memory(0x60)

        {
            // title

            let offset := mload(add(headStart, 0))
            if gt(offset, 0xffffffffffffffff) { revert_error_5e8f644817bc4960744f35c15999b6eff64ae702f94b1c46297cfd4e1aec2421() }

            mstore(add(value, 0x00), abi_decode_t_string_memory_ptr_fromMemory(add(headStart, offset), end))

        }

        {
            // options

            let offset := mload(add(headStart, 32))
            if gt(offset, 0xffffffffffffffff) { revert_error_5e8f644817bc4960744f35c15999b6eff64ae702f94b1c46297cfd4e1aec2421() }

            mstore(add(value, 0x20), abi_decode_t_array$_t_string_memory_ptr_$dyn_memory_ptr_fromMemory(add(headStart, offset), end))

        }

        {
            // deadline

            let offset := 64

            mstore(add(value, 0x40), abi_decode_t_uint256_fromMemory(add(headStart, offset), end))

        }

    }

    function abi_decode_tuple_t_struct$_voting_$442_memory_ptr_fromMemory(headStart, dataEnd) -> value0 {
        if slt(sub(dataEnd, headStart), 32) { revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() }

        {

            let offset := mload(add(headStart, 0))
            if gt(offset, 0xffffffffffffffff) { revert_error_c1322bf8034eace5e0b5c7295db60986aa89aae5e0ea0873e4689e076861a5db() }

            value0 := abi_decode_t_struct$_voting_$442_memory_ptr_fromMemory(add(headStart, offset), dataEnd)
        }

    }

    function array_storeLengthForEncoding_t_bytes_memory_ptr_nonPadded_inplace_fromStack(pos, length) -> updated_pos {
        updated_pos := pos
    }

    function abi_encode_t_bytes_memory_ptr_to_t_bytes_memory_ptr_nonPadded_inplace_fromStack(value, pos) -> end {
        let length := array_length_t_bytes_memory_ptr(value)
        pos := array_storeLengthForEncoding_t_bytes_memory_ptr_nonPadded_inplace_fromStack(pos, length)
        copy_memory_to_memory_with_cleanup(add(value, 0x20), pos, length)
        end := add(pos, length)
    }

    function abi_encode_tuple_packed_t_bytes_memory_ptr__to_t_bytes_memory_ptr__nonPadded_inplace_fromStack_reversed(pos , value0) -> end {

        pos := abi_encode_t_bytes_memory_ptr_to_t_bytes_memory_ptr_nonPadded_inplace_fromStack(value0,  pos)

        end := pos
    }

    function abi_decode_t_bytes32_fromMemory(offset, end) -> value {
        value := mload(offset)
        validator_revert_t_bytes32(value)
    }

    function abi_decode_tuple_t_bytes32_fromMemory(headStart, dataEnd) -> value0 {
        if slt(sub(dataEnd, headStart), 32) { revert_error_dbdddcbe895c83990c08b3492a0e83918d802a52331272ac6fdb6a7c4aea3b1b() }

        {

            let offset := 0

            value0 := abi_decode_t_bytes32_fromMemory(add(headStart, offset), dataEnd)
        }

    }

    function panic_error_0x12() {
        mstore(0, 35408467139433450592217433187231851964531694900788300625387963629091585785856)
        mstore(4, 0x12)
        revert(0, 0x24)
    }

    function mod_t_uint256(x, y) -> r {
        x := cleanup_t_uint256(x)
        y := cleanup_t_uint256(y)
        if iszero(y) { panic_error_0x12() }
        r := mod(x, y)
    }

}
