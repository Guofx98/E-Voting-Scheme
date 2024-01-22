// SPDX-License-Identifier: MIT
pragma solidity ^0.8.22;

import "./ModUtils.sol";
import "./Initiate.sol";
import "./Utils.sol";
contract Voting{

    struct Point {
        uint c0;
        uint256 x;
        uint256 y;
    }


    mapping (uint => Utils.record []) ballots;

    
    struct voters{
        mapping (uint256 => bool) isVote;
        bool isNotNull;
    }
    mapping (uint => voters) voterList;

    uint256 constant p = 21888242871839275222246405745257275088696311157297823662689037894645226208583;

    uint256 constant g = 61033774474184906981525079004870330119742361014454268741660576486386953477442;
    Initiate iv = Initiate(/* Initiate Contract Address */);

    function vote(uint round, uint256 B, uint256 a, uint256 pk, uint256 c0, uint256 ballotx, uint256 balloty) public {
        Point memory ballot  = Point(c0,ballotx,balloty);
        if(iv.getVoting(round).deadline==0||iv.getVoting(round).deadline<block.timestamp||voterList[round].isVote[pk]){
            return;
        }
        bytes memory m = toBytes(pk+B+ballot.x+ballot.y);
        bytes32 n = sha256(m);
        uint256 c = bytesToUint(n);
        uint256 v2 = ModUtils.modPow(g,c,p);
        if(ModUtils.modPow(g,a,p)==v2*B%p){
            Utils.record memory b = Utils.record(ballot.c0,ballot.x,ballot.y);
            ballots[round].push(b);
            voterList[round].isVote[pk]=true;
            voterList[round].isNotNull=true;
        }

    }

    function toBytes(uint256 x) public pure returns (bytes memory b) {
        b = new bytes(32);
        assembly { mstore(add(b, 32), x) }
    }

    function bytesToUint(bytes32 b) public pure returns (uint256) {
        uint256 number;
        for (uint256 i = 0; i < b.length; i++) {
            number = number + uint8(b[i]) * (2**(8 * (b.length - (i + 1))));
        }
        return number;
    }

    function getBallots(uint round) public view returns(Utils.record [] memory){
        return ballots[round];
    }

}