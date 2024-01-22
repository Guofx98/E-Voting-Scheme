// SPDX-License-Identifier: Apache-2.0

pragma solidity >=0.7.1;

library Utils{
    struct voting{
        string title;
        string [] options;
        uint deadline;
    }
    struct record {
        uint c0;
        uint256 x;
        uint256 y;
    }
}