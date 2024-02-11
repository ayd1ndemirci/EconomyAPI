<?php

namespace ayd1ndemirci\EconomyAPI\manager;

use ayd1ndemirci\EconomyAPI\database\SQLite;
use ayd1ndemirci\EconomyAPI\Economy;
use SOFe\AwaitGenerator\Await;

class Manager
{
    public SQLite $database;

    public function __construct()
    {
        $this->database = Economy::getInstance()->getDatabase();
    }


    public function getPlayerMoney(string $playerName): int
    {
        $money = 0;

        Await::f2c(function () use(&$money, $playerName) {
            $row = (array) yield from $this->database->getPlayerMoney($playerName);
            $money = $row[0]["money"];
        });
        $this->database->getDataConnector()->waitAll();
        return $money;
    }

    public function addPlayerMoney(string $playerName, int $money): void
    {
        $currently = $this->getPlayerMoney($playerName);
        $this->database->updatePlayerMoney($playerName, ($currently + $money), function (){});
    }

    public function takePlayerMoney(string $playerName, int $money): void
    {
        $currently = $this->getPlayerMoney($playerName);
        $this->database->updatePlayerMoney($playerName, ($currently - $money), function (){});
    }
}