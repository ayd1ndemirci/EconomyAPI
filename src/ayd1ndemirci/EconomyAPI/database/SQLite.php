<?php

namespace ayd1ndemirci\EconomyAPI\database;

use ayd1ndemirci\EconomyAPI\Economy;
use poggit\libasynql\DataConnector;
use poggit\libasynql\libasynql;
use Closure;
use SOFe\AwaitGenerator\Await;

class SQLite
{
    private DataConnector $dataConnector;

    public function __construct()
    {
        $this->dataConnector = libasynql::create(
            Economy::getInstance(),
            Economy::getInstance()->getConfig()->get("database"),
            ["sqlite" => "database/sqlite.sql"]
        );

        $this->dataConnector->executeGeneric("economy.createTable");
    }

    /**
     * @param string $playerName
     * @return void
     */
    public function addPlayer(string $playerName): void
    {
        $this->dataConnector->executeInsert("economy.addPlayer", [
            "playerName" => $playerName
        ]);
    }

    /**
     * @param string $playerName
     * @param int $money
     * @param Closure $closure
     * @return void
     */

    public function updatePlayerMoney(string $playerName, int $money, Closure $closure): void
    {
        $this->dataConnector->executeChange("economy.updatePlayerMoney", [
            "playerName" => $playerName,
            "money" => $money,
        ], function () use ($closure): void
        {
            $closure();
        });
    }

    /**
     * @param string $playerName
     * @return \Generator
     */
    public function getPlayerMoney(string $playerName): \Generator
    {
        $this->dataConnector->executeSelect("economy.getPlayerMoney", [
            "playerName" => $playerName
        ], yield, yield Await::REJECT);
        return yield Await::ONCE;
    }

    /**
     * @return DataConnector
     */

    public function getDataConnector() :DataConnector {
        return $this->dataConnector;
    }
}