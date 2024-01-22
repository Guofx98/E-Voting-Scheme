// SPDX-License-Identifier: Apache-2.0

pragma solidity >=0.7.1;

import "./Utils.sol";

contract Initiate{

    
    Utils.voting [] votingList;
    address constant C = /* C's Address */;

    function initiateVoting(string memory title,string [] memory options,uint deadline) public returns(uint index){
        Utils.voting memory v = Utils.voting(title,options,deadline);
        if(msg.sender!=C){
            return 100000;
        }
        votingList.push(v);
        return votingList.length-1;
    }

    function getVoting(uint index) public view returns(Utils.voting memory){
        if(index>=0&&index<votingList.length){
            return votingList[index];
        }
        string []  memory a;
        return Utils.voting("",a,0);
    }
}